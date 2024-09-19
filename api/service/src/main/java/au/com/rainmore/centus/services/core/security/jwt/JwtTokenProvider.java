package au.com.rainmore.centus.services.core.security.jwt;

import au.com.rainmore.centus.services.core.security.CurrentUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Algorithm algorithm;

    private final JWTVerifier jwtVerifier;

    @Autowired
    public JwtTokenProvider(Algorithm algorithm, JWTVerifier jwtVerifier) {
        this.algorithm = algorithm;
        this.jwtVerifier = jwtVerifier;
    }

    public String generateToken(CurrentUser currentUser) {
        String[] claims = buildClaims(currentUser);
        Instant issuedAt = Instant.now();
        return JWT.create()
                .withIssuer(SecurityConstants.GET_ARRAYS_LLC)
                .withAudience(SecurityConstants.GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(issuedAt)
                .withSubject(currentUser.getUsername())
                .withArrayClaim(SecurityConstants.AUTHORITIES, claims)
                .withExpiresAt(issuedAt.plusMillis(SecurityConstants.EXPIRATION_TIME))
                .sign(algorithm);
    }

    public Set<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public Authentication getAuthentication(
            String username,
            Set<GrantedAuthority> authorities,
            HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    public boolean isTokenValid(String token, String username) {
        return StringUtils.isNotEmpty(username) && isTokenExpired(token);
    }

    public String getSubject(String token) {
        return jwtVerifier.verify(token)
                .getSubject();
    }

    public HttpHeaders buildHttpHeaders(CurrentUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.JWT_TOKEN_HEADER, generateToken(currentUser));
        return headers;
    }

    private boolean isTokenExpired(String token) {
        return jwtVerifier.verify(token)
                .getExpiresAtAsInstant().isAfter(Instant.now());
    }

    private String[] getClaimsFromToken(String token) throws JWTVerificationException {
        return jwtVerifier.verify(token)
                .getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
    }

    private String[] buildClaims(CurrentUser currentUser) {
        return currentUser.getAuthorities().stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toArray(String[]::new);
    }

}

package au.com.rainmore.centus.configs.security.jwt;

import au.com.rainmore.centus.services.core.security.jwt.JwtTokenProvider;
import au.com.rainmore.centus.services.core.security.jwt.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
        }
        else {
            Optional<String> authorizationHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                    .filter(s -> s.startsWith(SecurityConstants.TOKEN_PREFIX));

//            if (authorizationHeader.isEmpty()) {
//                throw new AuthenticationCredentialsNotFoundException("No authorization header found");
//            }

            try {
                authorizationHeader.ifPresent( authorizationString -> {
                    String token = authorizationString.substring(SecurityConstants.TOKEN_PREFIX.length());
                    String username = jwtTokenProvider.getSubject(token);

                    if (jwtTokenProvider.isTokenValid(token, username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                        Set<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                        Authentication auth = jwtTokenProvider.getAuthentication(username, authorities, request);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                });
            }
            catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw new AuthenticationCredentialsNotFoundException(e.getMessage(), e);
            }
        }

        filterChain.doFilter(request, response);
    }
}

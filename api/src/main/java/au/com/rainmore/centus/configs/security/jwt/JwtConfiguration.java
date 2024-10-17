package au.com.rainmore.centus.configs.security.jwt;

import au.com.rainmore.centus.services.core.security.jwt.SecurityConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtConfiguration(
            JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public Algorithm getAlgorithm() {
        return Algorithm.HMAC512(jwtProperties.getSecret().getBytes());
    }

    @Bean
    public JWTVerifier getVerifier(Algorithm algorithm) throws JWTVerificationException {
        return JWT.require(algorithm).withIssuer(SecurityConstants.GET_ARRAYS_LLC).build();
    }

}

package au.com.rainmore.centus.configs.security;

import au.com.rainmore.centus.configs.json.JsonUtils;
import au.com.rainmore.centus.configs.security.jwt.JwtAccessDeniedHandler;
import au.com.rainmore.centus.configs.security.jwt.JwtAuthenticationEntryPoint;
import au.com.rainmore.centus.configs.security.jwt.JwtAuthorizationFilter;
import au.com.rainmore.centus.services.core.security.CurrentUserAuthenticationProvider;
import au.com.rainmore.centus.services.core.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthorizationFilter            jwtAuthorizationFilter;
    private final JwtAuthenticationEntryPoint       jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler            jwtAccessDeniedHandler;
    private final CurrentUserAuthenticationProvider currentUserAuthenticationProvider;


    private static final String[] PUBLIC_URLS = {
            "/api-docs/**",
            "/swagger-ui/**",
            "/h2-api-console/**",
            "/auth/login",
            "/user/register",
            "/user/forgot-password",
            "/user/forgot-password/reset-password"
    };

    @Autowired
    public SecurityConfiguration(
            JsonUtils jsonUtils,
            JwtTokenProvider jwtTokenProvider,
            CurrentUserAuthenticationProvider currentUserAuthenticationProvider) {
        this.jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtTokenProvider);
        this.jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(jsonUtils);
        this.jwtAccessDeniedHandler = new JwtAccessDeniedHandler(jsonUtils);
        this.currentUserAuthenticationProvider = currentUserAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizationRequestRegistry -> {
                    authorizationRequestRegistry.requestMatchers(PUBLIC_URLS).permitAll();
                    authorizationRequestRegistry.anyRequest().authenticated();
                })
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpHeadersConfigurer ->
                        // added to support H2 url only.
                        httpHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        ;

        return http.build();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(currentUserAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

}

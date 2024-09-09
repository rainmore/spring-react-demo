package au.com.rainmore.centus.configs.security.jwt;

import au.com.rainmore.centus.configs.json.JsonResponse;
import au.com.rainmore.centus.configs.json.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    private final JsonUtils jsonUtils;

    public JwtAuthenticationEntryPoint(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException arg2) throws IOException {

        JsonResponse<String> jsonResponse = new JsonResponse<>(
                null, HttpStatus.UNAUTHORIZED.getReasonPhrase()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().append(jsonUtils.toJsonString(jsonResponse));
    }
}

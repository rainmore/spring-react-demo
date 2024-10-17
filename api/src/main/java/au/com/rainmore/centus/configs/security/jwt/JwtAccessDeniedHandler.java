package au.com.rainmore.centus.configs.security.jwt;

import au.com.rainmore.centus.configs.json.JsonResponse;
import au.com.rainmore.centus.configs.json.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final JsonUtils jsonUtils;

    public JwtAccessDeniedHandler(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        JsonResponse<String> jsonResponse = new JsonResponse<>(
                null, httpStatus.getReasonPhrase()
        );

        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append(jsonUtils.toJsonString(jsonResponse));
    }
}

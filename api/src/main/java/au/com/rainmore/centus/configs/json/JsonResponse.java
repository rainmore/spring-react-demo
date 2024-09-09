package au.com.rainmore.centus.configs.json;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

public record JsonResponse<T>(
        T data,
        String[] messages,
        LocalDateTime timestamp
) {

    public JsonResponse(T data, String[] messages) {
        this(data, messages, LocalDateTime.now());
    }

    public JsonResponse(T data, String message) {
        this(data, new String[]{message});
    }

    public JsonResponse(T data, List<String> messages) {
        this(data, messages.toArray(new String[0]));
    }

    public ResponseEntity<Object> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

    public ResponseEntity<Object> toResponseEntity(HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(this, headers, status);
    }

    public ResponseEntity<Object> responseOk() {
        return toResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<Object> responseForbidden() {
        return toResponseEntity(HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> responseBadRequest() {
        return toResponseEntity(HttpStatus.BAD_REQUEST);
    }

}


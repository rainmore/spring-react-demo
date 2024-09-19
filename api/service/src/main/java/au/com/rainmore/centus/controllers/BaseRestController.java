package au.com.rainmore.centus.controllers;

import au.com.rainmore.centus.configs.json.JsonResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseRestController {


    @ExceptionHandler
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        JsonResponse<Object> jsonResponse = new JsonResponse<>(null, status.getReasonPhrase());
        return jsonResponse.toResponseEntity(status);
    }

}

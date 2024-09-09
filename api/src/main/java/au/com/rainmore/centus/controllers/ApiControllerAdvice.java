package au.com.rainmore.centus.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, editor);
    }

    @ModelAttribute("requestContextPath")
    public String requestContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex,
            WebRequest request) {
        return handleExceptionInternal(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value
            = { AuthenticationCredentialsNotFoundException.class, AuthenticationException.class })
    protected ResponseEntity<Object> handleUnauthorized(
            RuntimeException ex,
            WebRequest request) {
        return handleExceptionInternal(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(value
            = { AccessDeniedException.class })
    protected ResponseEntity<Object> handleForbidden(
            RuntimeException ex,
            WebRequest request) {
        return handleExceptionInternal(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(message, httpStatus);
    }

}


package com.LDLS.Litigation.Project.diary.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap();
        body.put("message", ex.getMessage());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> body = new HashMap();
        String var10002 = ex.getName();
        body.put("message", "Type mismatch for argument " + var10002 + ": " + ex.getRequiredType().getSimpleName());
        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred");
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> body = new HashMap();
        body.put("message", ex.getMessage());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }
}

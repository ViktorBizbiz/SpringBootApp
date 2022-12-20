package ua.bizbiz.springbootapp.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.bizbiz.springbootapp.exception.HttpException;
import ua.bizbiz.springbootapp.exception.response.ErrorResponse;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpException ex) {
        ErrorResponse errorResponse = ErrorResponse.createErrorResponse(ex.getStatusCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

}

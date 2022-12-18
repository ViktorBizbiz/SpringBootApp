package ua.bizbiz.springbootapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public class HttpException extends RuntimeException {

    private HttpStatusCode statusCode;
    private String message;

}

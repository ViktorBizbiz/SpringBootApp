package ua.bizbiz.springbootapp.exception.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String httpStatusCode;
    private String httpStatusMessage;

    public static ErrorResponse createErrorResponse(HttpStatusCode code, String message) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatusCode(code.toString())
                .httpStatusMessage(message)
                .build();
    }
}

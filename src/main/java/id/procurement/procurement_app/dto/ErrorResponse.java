package id.procurement.procurement_app.dto;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        OffsetDateTime timestamp,
        String path
) { public static ErrorResponse of (HttpStatus status, String message, String path) {
    return new ErrorResponse(
            status.value(),
            status.getReasonPhrase(),
            message,
            OffsetDateTime.now(),
            path
    );
}
}

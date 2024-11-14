package authn_server.exception;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String message;

}

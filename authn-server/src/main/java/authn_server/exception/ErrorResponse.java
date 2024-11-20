package authn_server.exception;

import lombok.*;
/* This class is used to get the error message and errorCode if any rest api throws exception */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private String message;

}

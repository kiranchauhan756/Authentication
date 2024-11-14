package authn_server.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class ClientAlreadyExistException extends RuntimeException {
    private String message;
}

package authn_server.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/* this class is used to handle the exception if a user is trying to create a client which already exists in the database*/

@AllArgsConstructor
@NoArgsConstructor
public class ClientAlreadyExistException extends RuntimeException {
    private String message;
}

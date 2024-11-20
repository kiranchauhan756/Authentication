package authn_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* this class is used to handle the exception if a user is trying to access  a client which do not exist in the database*/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoSuchClientExistException  extends RuntimeException{
    private String message;

}

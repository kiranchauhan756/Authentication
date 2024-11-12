package authn_server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAlreadyExistException.class)
    public ResponseEntity<ClientErrorResponse> handleClientAlreadyExistException(Exception exc) {
        return new ResponseEntity<>(ClientErrorResponse.builder().message(exc.getMessage()).errorCode("AUTHN-001").build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchClientExistException.class)
    public ResponseEntity<ClientErrorResponse> handleNoSuchClientExistException(Exception exc) {
        return new ResponseEntity<>(ClientErrorResponse.builder().message(exc.getMessage()).errorCode("AUTHN-002").build(), HttpStatus.NOT_FOUND);
    }

}

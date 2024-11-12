package authn_server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ClientErrorResponse> handleException(Exception exc) {

        return new ResponseEntity<ClientErrorResponse>(ClientErrorResponse.builder().message(exc.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

}

package authn_server.exception;

import authn_server.helpers.JsonHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* this class is used to handle exceptions across the entire application in a global */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleClientAlreadyExistException(ClientAlreadyExistException exc) {
        return new ResponseEntity<>(ErrorResponse.builder().message(exc.getMessage()).errorCode("AUTHN-001").build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchClientExistException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchClientExistException(NoSuchClientExistException exc) {
        return new ResponseEntity<>(ErrorResponse.builder().message(exc.getMessage()).errorCode("AUTHN-002").build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        fieldErrors.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ResponseEntity<>(ErrorResponse.builder().errorCode("AUTHN-003").message(JsonHelper.asJsonString(errors)).build(), HttpStatus.BAD_REQUEST);
    }


}

package attache.devs.popote.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler(FileIsNotImageException.class)
    public ResponseEntity<Object> handleFileIsNotImageException(FileIsNotImageException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileSizeNotValidException.class)
    public ResponseEntity<Object> handleFileSizeNotValidException(FileSizeNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerImageNotFoundException.class)
    public ResponseEntity<Object> handleCustomerImageNotFoundException(CustomerImageNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}

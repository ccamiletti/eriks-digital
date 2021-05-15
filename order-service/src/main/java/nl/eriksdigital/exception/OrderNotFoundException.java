package nl.eriksdigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    private HttpStatus status;

    public OrderNotFoundException(Long id) {
        this(HttpStatus.NOT_FOUND, String.format("order with id: %d not found", id));
    }

    public OrderNotFoundException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }

}

package nl.eriksdigital.exception;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "error")
public class ErrorResponse {

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

    //Getter and setters

    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }


}

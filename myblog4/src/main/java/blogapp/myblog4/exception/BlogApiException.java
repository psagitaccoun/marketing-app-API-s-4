package blogapp.myblog4.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{

   private String message;

    public BlogApiException(String message) {
        this.message = message;
    }

    public BlogApiException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

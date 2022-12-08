package canard.intern.post.following.backend.exceptions;

public class UpdateException extends RuntimeException {
    public UpdateException() {
        super("Y'a un soucis avec l'écriture dans la base de donnée");
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }

    public UpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

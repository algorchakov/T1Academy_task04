package t1.openschool.task04.exeption;

public class AuthException extends RuntimeException{
    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }
}

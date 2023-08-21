package pl.dmcs.Spring6.Exception;

public class NotFoundException extends Exception {
    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
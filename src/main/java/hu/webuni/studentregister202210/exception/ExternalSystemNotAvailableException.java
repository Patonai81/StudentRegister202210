package hu.webuni.studentregister202210.exception;

public class ExternalSystemNotAvailableException extends RuntimeException{

    public ExternalSystemNotAvailableException(String message) {
        super(message);
    }
}

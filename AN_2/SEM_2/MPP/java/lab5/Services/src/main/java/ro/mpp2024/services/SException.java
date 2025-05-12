package ro.mpp2024.services;

public class SException extends Exception {
    public SException() {
    }

    public SException(String message) {
        super(message);
    }

    public SException(String message, Throwable cause) {
        super(message, cause);
    }
}
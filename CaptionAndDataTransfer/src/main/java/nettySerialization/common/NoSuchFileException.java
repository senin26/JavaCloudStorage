package nettySerialization.common;

public class NoSuchFileException extends Exception {
    public NoSuchFileException(String message, Throwable cause) {
        super("Such file wasn't found in the specified directory", cause);
    }
}

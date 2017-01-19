package by.training.xml_analyzer.exception;

public class AnalyzerException extends Exception {

    public AnalyzerException() {}

    public AnalyzerException(String message) {
        super(message);
    }

    public AnalyzerException(Throwable cause) {
        super(cause);
    }

    public AnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalyzerException(String message, Exception exception) {
        super(message, exception);

    }
}

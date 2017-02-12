package by.training.xml_validator.bean.web_app;

public class ErrorPage {
    private String exceptionType;
    private String errorCode;
    private String location;

    public ErrorPage() {
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return null;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

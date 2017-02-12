package by.training.xml_validator.bean.web_app;

public class ServletMapping {
    private String servletName;
    private String urlPattern;

    public ServletMapping() {
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public String toString() {
        return null;
    }
}

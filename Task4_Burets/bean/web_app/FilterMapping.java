package by.training.xml_validator.bean.web_app;

public class FilterMapping {
    private String filterName;
    private String urlPattern;
    private String dispatcher;

    public FilterMapping() {
    }

    public String getFilterName() {
        return filterName;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    @Override
    public String toString() {
        return null;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }
}

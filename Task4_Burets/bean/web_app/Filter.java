package by.training.xml_validator.bean.web_app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Filter {
    private String filterName;
    private String filterClass;
    private List<InitParam> initParams;

    public Filter() {
    }

    public String getFilterName() {
        return filterName;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public List<InitParam> getInitParams() {
        return initParams;
    }

    @Override
    public String toString() {
        return null;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public void setInitParams(List<InitParam> initParams) {
        this.initParams = initParams;
    }
}

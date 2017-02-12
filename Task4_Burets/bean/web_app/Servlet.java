package by.training.xml_validator.bean.web_app;

import java.util.List;

public class Servlet {
    private String servletName;
    private String servletClass;
    private List<InitParam> initParams;

    public Servlet() {
    }

    public String getServletName() {
        return servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public List<InitParam> getInitParams() {
        return initParams;
    }

    @Override
    public String toString() {
        return null;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public void setInitParams(List<InitParam> initParams) {
        this.initParams = initParams;
    }
}

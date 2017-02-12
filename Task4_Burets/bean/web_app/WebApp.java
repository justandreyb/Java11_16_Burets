package by.training.xml_validator.bean.web_app;

import java.util.ArrayList;
import java.util.List;

public class WebApp {
    private List<Servlet> servlets;
    private List<ServletMapping> servletMappings;
    private List<Filter> filters;
    private List<FilterMapping> filterMappings;
    private List<Listener> listeners;
    private List<ErrorPage> errorPages;
    private WelcomeFileList welcomeFileList;
    private String displayName;

    public WebApp() {
        this.servlets = new ArrayList<Servlet>();
        this.filters = new ArrayList<Filter>();
        this.errorPages = new ArrayList<ErrorPage>();
        this.listeners = new ArrayList<Listener>();
        this.servletMappings = new ArrayList<ServletMapping>();
        this.filterMappings = new ArrayList<FilterMapping>();
    }

    public void addServlet(Servlet servlet) {
        servlets.add(servlet);
    }

    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }

    public void addServletMapping(ServletMapping servletMapping) {
        this.servletMappings.add(servletMapping);
    }

    public void addFilterMapping(FilterMapping filterMapping) {
        this.filterMappings.add(filterMapping);
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void addErrorPage(ErrorPage errorPage) {
        this.errorPages.add(errorPage);
    }

    public void setWelcomeFileList(WelcomeFileList welcomeFileList) {
        this.welcomeFileList = welcomeFileList;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("display-name: ");
        result.append(displayName);
        result.append("\n");
        result.append("welcome-file-list: ");
        for (String welcomeFile : welcomeFileList.getWelcomeFiles()) {
            result.append("\n\t").append(welcomeFile);
        }
        result.append("\n");
        for (Filter filter : filters) {
            result.append(filter.toString());
        }
        result.append("\n");
        for (FilterMapping filterMapping : filterMappings) {
            result.append(filterMapping.toString());
        }
        result.append("\n");
        for (Servlet servlet : servlets) {
            result.append(servlet.toString());
        }
        result.append("\n");
        for (ServletMapping servletMapping : servletMappings) {
            result.append(servletMapping.toString());
        }
        result.append("\n");
        for (Listener listener : listeners) {
            result.append(listener.toString());
        }
        result.append("\n");
        for (ErrorPage errorPage : errorPages) {
            errorPage.toString();
        }

        return result.toString();
    }
}


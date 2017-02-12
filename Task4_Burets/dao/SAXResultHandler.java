package by.training.xml_validator.dao;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.bean.web_app.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.events.Attribute;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SAXResultHandler extends DefaultHandler {
    private StringBuilder charactersStorage;
    private boolean inFilterMapping = false;
    private boolean inServlet = false;

    private ErrorPage errorPage;
    private Filter filter;
    private FilterMapping filterMapping;
    private List<InitParam> initParams;
    private InitParam initParam;
    private Listener listener;
    private Servlet servlet;
    private ServletMapping servletMapping;
    private WelcomeFileList welcomeFileList;

    private WebApp webApp;
    private WebXML webXML;

    public SAXResultHandler() {
        webXML = new WebXML();
        webApp = webXML.getWebApp();
        charactersStorage = new StringBuilder();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void startElement(String uri, String localName, String currentElement, Attributes attributes) throws SAXException {

        switch (currentElement) {
            case "web-app":
                String id = attributes.getValue("id");
                webXML.setId(id);

                String version = attributes.getValue("version");
                webXML.setVersion(version);

                break;
            case "welcome-file-list":
                welcomeFileList = new WelcomeFileList();
                break;
            case "filter":
                filter = new Filter();
                break;
            case "init-param":
                initParam = new InitParam();
                break;
            case "filter-mapping":
                filterMapping = new FilterMapping();
                inFilterMapping = true;
                break;
            case "listener":
                listener = new Listener();
                break;
            case "servlet":
                servlet = new Servlet();
                inServlet = true;
                break;
            case "servlet-mapping":
                servletMapping = new ServletMapping();
                break;
            case "error-page":
                errorPage = new ErrorPage();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String currentElement) throws SAXException {
        switch (currentElement) {
            case "display-name":
                webApp.setDisplayName(getValue());
                break;
            case "welcome-file":
                welcomeFileList.addWelcomeFile(getValue());
                break;
            case "filter-name":
                if (inFilterMapping) {
                    filterMapping.setFilterName(getValue());
                } else {
                    filter.setFilterName(getValue());
                }
                break;
            case "filter-class":
                filter.setFilterClass(getValue());
                break;
            case "param-name":
                initParam.setParamName(getValue());
                break;
            case "param-value":
                initParam.setParamValue(getValue());
                break;
            case "init-param":
                if (initParams == null) {
                    initParams = new ArrayList<InitParam>(2);
                }
                initParams.add(initParam);
                break;
            case "url-pattern":
                if (inFilterMapping) {
                    filterMapping.setUrlPattern(getValue());
                } else {
                    servletMapping.setUrlPattern(getValue());
                }
                break;
            case "listener-class":
                listener.setListenerClass(getValue());
                break;
            case "servlet-name":
                if (inServlet) {
                    servlet.setServletName(getValue());
                } else {
                    servletMapping.setServletName(getValue());
                }
                break;
            case "servlet-class":
                servlet.setServletClass(getValue());
                break;
            case "exception-type":
                errorPage.setExceptionType(getValue());
                break;
            case "error-code":
                errorPage.setErrorCode(getValue());
                break;
            case "location":
                errorPage.setLocation(getValue());
                break;

            case "welcome-file-list":
                webApp.setWelcomeFileList(welcomeFileList);
                break;
            case "filter":
                filter.setInitParams(initParams);
                webApp.addFilter(filter);
                break;
            case "filter-mapping":
                webApp.addFilterMapping(filterMapping);
                inFilterMapping = false;
                break;
            case "listener":
                webApp.addListener(listener);
                break;
            case "servlet":
                servlet.setInitParams(initParams);
                webApp.addServlet(servlet);
                inServlet = false;
                break;
            case "servlet-mapping":
                webApp.addServletMapping(servletMapping);
                break;
            case "error-page":
                webApp.addErrorPage(errorPage);
                break;
        }
        charactersStorage.setLength(0);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        charactersStorage.append(ch, start, length);
    }

    public WebXML getWebXML() {
        return webXML;
    }

    private String getValue() {
        return charactersStorage.toString();
    }

    @Override
    public String toString() {
        //https://www.tutorialspoint.com/java_xml/java_sax_parse_document.htm
        return null;
    }
}

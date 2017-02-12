package by.training.xml_validator.service.impl;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.bean.web_app.*;
import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.exception.DAOException;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserDOMImpl implements Parser {
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

    @Override
    public String validate(FileXML fileXML) throws ServiceException {
        String response;

        DAOFactory factory = DAOFactory.getInstance();
        try {
            SchemaXSD schemaXSD = factory.getSchemaXSD();
            Schema schema = schemaXSD.getSchema();

            Validator validator = schema.newValidator();
            try {
                validator.validate(fileXML.getSource());
                response = "File is valid";
            } catch (SAXException e) {
                response = "File isn't valid, reason : " + e.getMessage();
            }
        } catch (IOException | DAOException e) {
            throw new ServiceException(e);
        } finally {
            try {
                fileXML.returnSource();
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return response;
    }

    @Override
    public WebXML parse(FileXML fileXML) throws ServiceException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        WebXML webXML = null;

        try {
            docBuilder = dbFactory.newDocumentBuilder();

            Document document = docBuilder.parse(fileXML.getStream());
            document.getDocumentElement().normalize();

            webXML = createWebXML(document);

        } catch (IOException | SAXException | DAOException e) {
            throw new ServiceException(e);
        } catch (ParserConfigurationException e) {
            throw new ServiceException(e);
        } finally {
            try {
                fileXML.closeStream();
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }

        return webXML;
    }

    private WebXML createWebXML(Document document) throws ServiceException {
        WebXML webXML = new WebXML();

        String webAppID = document.getDocumentElement().getAttribute("id");
        String webAppVersion = document.getDocumentElement().getAttribute("version");

        webXML.setId(webAppID);
        webXML.setVersion(webAppVersion);

        NodeList webAppList = document.getElementsByTagName("web-app");
        Node webAppElement = webAppList.item(0);

        WebApp webApp = createWebApp(webAppElement);
        webXML.setWebApp(webApp);

        return webXML;
    }

    private WebApp createWebApp(Node webAppElement) throws ServiceException {
        webApp = new WebApp();

        if (!webAppElement.hasChildNodes()) {
            throw new ServiceException("Empty web-app");
        }

        NodeList webAppDoc = webAppElement.getChildNodes();
        for (int currentElementIdx = 0; currentElementIdx < webAppDoc.getLength(); currentElementIdx++) {
            Node currentElement = webAppDoc.item(currentElementIdx);

            String currentElementName = currentElement.getNodeName();

            if (!isElementNode(currentElement)) {
                continue;
            }

            switch (currentElementName) {
                case "welcome-file-list":
                    welcomeFileList = new WelcomeFileList();
                    break;
                case "filter":
                    inServlet = false;
                    filter = new Filter();
                    break;
                case "init-param":
                    if (initParams == null) {
                        initParams = new ArrayList<InitParam>(2);
                    }
                    initParam = new InitParam();
                    break;
                case "filter-mapping":
                    inFilterMapping = true;
                    filterMapping = new FilterMapping();
                    break;
                case "listener":
                    listener = new Listener();
                    break;
                case "servlet":
                    inServlet = true;
                    servlet = new Servlet();
                    break;
                case "servlet-mapping":
                    servletMapping = new ServletMapping();
                    break;
                case "error-page":
                    errorPage = new ErrorPage();
                    break;
            }

            analyzeInnerBlock(currentElement);
        }
        return webApp;
    }

    private boolean isElementNode(Node element) {
        return element.getNodeType() == Node.ELEMENT_NODE;
    }

    private void analyzeInnerBlock(Node element) throws ServiceException {
        NodeList innerBlock = element.getChildNodes();
        for (int innerElementIdx = 0; innerElementIdx < innerBlock.getLength(); innerElementIdx++) {
            Node innerElement = innerBlock.item(innerElementIdx);
            if (!isElementNode(innerElement)) {
                continue;
            }

            switch (innerElement.getNodeName()) {
                case "display-name":
                    webApp.setDisplayName(getValue(innerElement));
                    break;
                case "welcome-file":
                    welcomeFileList.addWelcomeFile(getValue(innerElement));
                    break;
                case "filter-name":
                    if (inFilterMapping) {
                        filterMapping.setFilterName(getValue(innerElement));
                    } else {
                        filter.setFilterName(getValue(innerElement));
                    }
                    break;
                case "filter-class":
                    filter.setFilterClass(getValue(innerElement));
                    break;
                case "param-name":
                    initParam.setParamName(getValue(innerElement));
                    break;
                case "param-value":
                    initParam.setParamValue(getValue(innerElement));
                    break;
                case "init-param":
                    if (initParams != null) {
                        initParams.add(initParam);
                    }
                    break;
                case "url-pattern":
                    if (inFilterMapping) {
                        filterMapping.setUrlPattern(getValue(innerElement));
                    } else {
                        servletMapping.setUrlPattern(getValue(innerElement));
                    }
                    break;
                case "listener-class":
                    listener.setListenerClass(getValue(innerElement));
                    break;
                case "servlet-name":
                    if (inServlet) {
                        servlet.setServletName(getValue(innerElement));
                    } else {
                        servletMapping.setServletName(getValue(innerElement));
                    }
                    break;
                case "servlet-class":
                    servlet.setServletClass(getValue(innerElement));
                    break;
                case "exception-type":
                    errorPage.setExceptionType(getValue(innerElement));
                    break;
                case "error-code":
                    errorPage.setErrorCode(getValue(innerElement));
                    break;
                case "location":
                    errorPage.setLocation(getValue(innerElement));
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
        }
    }

    private String getValue(Node element) {
        return element.getTextContent();
    }
}

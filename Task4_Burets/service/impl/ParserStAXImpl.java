package by.training.xml_validator.service.impl;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.bean.web_app.*;
import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.exception.DAOException;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;
import org.xml.sax.SAXException;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ParserStAXImpl implements Parser {
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

    @Override
    public String validate(FileXML fileXML) throws ServiceException {
        String response;

        DAOFactory factory = DAOFactory.getInstance();
        try {
            SchemaXSD schemaXSD = factory.getSchemaXSD();
            Schema schema = schemaXSD.getSchema();

            Validator validator = schema.newValidator();

            XMLInputFactory staxFactory = XMLInputFactory.newInstance();
            staxFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
            staxFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
            staxFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
            staxFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);

            try {
                XMLStreamReader xmlr = staxFactory.createXMLStreamReader(fileXML.getStream());
                validator.validate(new StAXSource(xmlr));
                response = "File is valid";
            } catch (XMLStreamException e) {
                response = "File isn't valid";
            }
        } catch (IOException | DAOException | SAXException e) {
            throw new ServiceException(e);
        } finally {
            try {
                fileXML.closeStream();
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }

        return response;
    }

    @Override
    public WebXML parse(FileXML fileXML) throws ServiceException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = null;
        WebXML webXML = null;

        try {
            eventReader = factory.createXMLEventReader(fileXML.getStream());
            webXML = createWebXML(eventReader);

        } catch (XMLStreamException | DAOException e) {
            throw new ServiceException(e);
        } finally {
            try {
                fileXML.closeStream();
                if (eventReader != null) {
                    eventReader.close();
                }
            } catch (DAOException | XMLStreamException e) {
                throw new ServiceException(e);
            }
        }

        return webXML;
    }

    private WebXML createWebXML(XMLEventReader eventReader) throws ServiceException {
        WebXML webXML = new WebXML();
        charactersStorage = new StringBuilder();
        initParams = new LinkedList<InitParam>();
        try {
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                analyzeEvent(event, webXML);
            }
        } catch (XMLStreamException e) {
            throw new ServiceException(e);
        }
        return webXML;
    }

    private void analyzeEvent(XMLEvent event, WebXML webXML) {
        switch(event.getEventType()){
            case XMLStreamConstants.START_ELEMENT:
                StartElement startElement = event.asStartElement();
                processStartElement(startElement, webXML);
                break;
            case XMLStreamConstants.CHARACTERS:
                Characters characters = event.asCharacters();
                charactersStorage.append(characters.getData());
                break;
            case  XMLStreamConstants.END_ELEMENT:
                EndElement endElement = event.asEndElement();
                processEndElement(endElement, webXML.getWebApp());
                break;
        }
    }

    private void processStartElement(StartElement startElement, WebXML webXML) {
        String currentTag = startElement.getName().getLocalPart();
        switch (currentTag.toLowerCase()) {
            case "web-app":
                Iterator<Attribute> attributes = startElement.getAttributes();
                if (attributes.hasNext()) {
                    String id = attributes.next().getValue();
                    webXML.setId(id);
                }
                if (attributes.hasNext()) {
                    String version = attributes.next().getValue();
                    webXML.setVersion(version);
                }

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

    private void processEndElement(EndElement endElement, WebApp webApp) {
        switch (endElement.getName().getLocalPart()) {
            case "display-name":
                webApp.setDisplayName(getValue());
                charactersStorage.setLength(0);
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

    private String getValue() {
        return charactersStorage.toString();
    }
}

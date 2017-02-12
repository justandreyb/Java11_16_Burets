package by.training.xml_validator.service.impl;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.dao.SAXResultHandler;
import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.exception.DAOException;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import java.io.IOException;

public class ParserSAXImpl implements Parser {
    @Override
    public String validate(FileXML fileXML) throws ServiceException {
        String response;

        DAOFactory factory = DAOFactory.getInstance();
        try {
            SchemaXSD schemaXSD = factory.getSchemaXSD();
            Schema schema = schemaXSD.getSchema();

            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setSchema(schema);

            SAXParser parser = spf.newSAXParser();
            DefaultHandler handler = new DefaultHandler();
            try {
                parser.parse(fileXML.getStream(), handler);
                response = "File is valid";
            } catch (SAXException e) {
                response = "File isn't valid, reason : " + e.getMessage();
            }
        } catch (IOException | DAOException | SAXException e) {
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

        return response;
    }

    @Override
    public WebXML parse(FileXML fileXML) throws ServiceException {
        WebXML webXML;

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DAOFactory daoFactory = DAOFactory.getInstance();
            SAXResultHandler handler = daoFactory.getSaxResultHandler();

            saxParser.parse(fileXML.getStream(), handler);

            webXML = createWebXML(handler);
        } catch (SAXException | IOException | DAOException e) {
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

    private WebXML createWebXML(SAXResultHandler handler) {
        return handler.getWebXML();
    }
}

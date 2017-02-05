package by.training.xml_validator.service.impl;

import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.exception.DAOException;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;
import jdk.internal.org.xml.sax.ErrorHandler;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

public class ParserStAXImpl implements Parser {

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
        }

        return response;
    }
}

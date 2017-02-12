package by.training.xml_validator.dao.factory;

import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.SAXResultHandler;
import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.impl.FileXMLImpl;
import by.training.xml_validator.dao.impl.SchemaXSDImpl;

public class DAOFactory {
    private final static DAOFactory instance = new DAOFactory();

    private DAOFactory() {}

    private final FileXML fileXML = new FileXMLImpl();
    private final SchemaXSD schemaXSD = new SchemaXSDImpl();
    private final SAXResultHandler saxResultHandler = new SAXResultHandler();

    public static DAOFactory getInstance() {
        return instance;
    }

    public FileXML getFileXML() {
        return fileXML;
    }

    public SchemaXSD getSchemaXSD() {
        return schemaXSD;
    }

    public SAXResultHandler getSaxResultHandler() {
        return saxResultHandler;
    }
}

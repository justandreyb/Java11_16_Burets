package by.training.xml_validator.dao.impl;

import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.exception.DAOException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class SchemaXSDImpl implements SchemaXSD {
    private final static String schemaPath = System.getProperty("user.dir") + "/src/by/training/resources/web.xsd";

    @Override
    public Schema getSchema() throws DAOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaFile;
        Schema schema;
        try {
            schemaFile = new File(schemaPath);
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException e) {
            throw new DAOException(e);
        }

        return schema;
    }
}

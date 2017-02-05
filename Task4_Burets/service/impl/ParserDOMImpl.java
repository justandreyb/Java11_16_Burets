package by.training.xml_validator.service.impl;

import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.SchemaXSD;
import by.training.xml_validator.dao.exception.DAOException;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.IOException;

public class ParserDOMImpl implements Parser {
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
        }
        return response;
    }
}

package by.training.xml_validator.service.impl;

import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;

import java.io.InputStream;

public class WrongParser implements Parser {
    @Override
    public String validate(FileXML fileXML) throws ServiceException {
        throw new ServiceException("Wrong parser");
    }
}

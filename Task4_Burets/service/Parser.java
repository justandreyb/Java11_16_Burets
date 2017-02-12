package by.training.xml_validator.service;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.service.exception.ServiceException;

public interface Parser {
    String validate(FileXML fileXML) throws ServiceException;
    WebXML parse(FileXML fileXML) throws ServiceException;
}

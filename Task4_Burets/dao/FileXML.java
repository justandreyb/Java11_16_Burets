package by.training.xml_validator.dao;

import by.training.xml_validator.dao.exception.DAOException;

import javax.xml.transform.Source;
import java.io.BufferedInputStream;

public interface FileXML {
    void setFile(String filePath);
    BufferedInputStream getStream() throws DAOException;
    void closeStream() throws DAOException;
    Source getSource() throws DAOException;
    void returnSource() throws DAOException;
}

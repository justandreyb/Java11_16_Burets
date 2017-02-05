package by.training.xml_validator.dao.impl;

import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.exception.DAOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileXMLImpl implements FileXML {

    private String filePath;
    private BufferedInputStream stream;

    @Override
    public void setFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public BufferedInputStream getStream() throws DAOException {
        if (!isValidFilePath(filePath)) {
            throw new DAOException("Wrong file path");
        }
        try {
            stream = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new DAOException(e);
        }
        return stream;
    }

    @Override
    public Source getSource() throws DAOException {
        if (stream == null) {
            throw new DAOException("Error while get a file stream");
        }
        return new StreamSource(stream);
    }

    @Override
    public void returnSource() throws DAOException {
        closeStream();
    }

    @Override
    public void closeStream() throws DAOException {
        try {
           if (stream != null) {
               stream.close();
           }
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    private boolean isValidFilePath(String filePath) {
        return filePath != null && !filePath.isEmpty();
    }
}

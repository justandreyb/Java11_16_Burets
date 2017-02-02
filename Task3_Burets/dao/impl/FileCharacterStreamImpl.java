package by.training.xml_analyzer.dao.impl;

import by.training.xml_analyzer.dao.CharacterStream;
import by.training.xml_analyzer.dao.exception.DAOException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileCharacterStreamImpl implements CharacterStream {

    private BufferedInputStream fileInputStream;

    public FileCharacterStreamImpl() {}

    @Override
    public void setFilePath(String filePath) throws DAOException {
        try {
            this.fileInputStream = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int getNext() throws DAOException {
        try {
            return fileInputStream.read();
        }
        catch(IOException ex){
            throw new DAOException(ex.getMessage());
        }
    }

    @Override
    public void closeStream() throws DAOException {
        try {
            this.fileInputStream.close();
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }
}

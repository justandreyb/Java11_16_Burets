package by.training.xml_analyzer.dao;

import by.training.xml_analyzer.dao.exception.DAOException;

public interface CharacterStream {

    void setFilePath(String filePath) throws DAOException;

    int getNext() throws DAOException;

    void closeStream() throws DAOException;
}

package by.training.xml_validator.dao;

import by.training.xml_validator.dao.exception.DAOException;

import javax.xml.validation.Schema;

public interface SchemaXSD {
    Schema getSchema() throws DAOException;
}

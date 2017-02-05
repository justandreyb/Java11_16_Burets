package by.training.xml_validator.controller.impl.command;

import by.training.xml_validator.bean.ParserName;
import by.training.xml_validator.controller.Command;
import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.exception.DAOException;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;
import by.training.xml_validator.service.factory.ParserFactory;
import by.training.xml_validator.util.ArgumentParser;
import by.training.xml_validator.util.exception.UtilException;

import java.io.InputStream;

public class ValidateViaParser implements Command {
    private ParserName parserName;

    @Override
    public String validate(String request) {
        String response;

        try {
            String filePath = ArgumentParser.getFilePath(request);

            DAOFactory daoFactory = DAOFactory.getInstance();

            FileXML fileXML = daoFactory.getFileXML();
            fileXML.setFile(filePath);

            ParserFactory factory = ParserFactory.getInstance();
            Parser parser = factory.getParser(parserName);

            response = parser.validate(fileXML);
        } catch (ServiceException | UtilException e) {
            //log
            response = "Error while validating via parser";
        }

        return response;
    }

    @Override
    public void setParserName(ParserName parserName) {
        this.parserName = parserName;
    }
}

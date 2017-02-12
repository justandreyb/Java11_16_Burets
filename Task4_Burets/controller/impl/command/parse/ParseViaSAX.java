package by.training.xml_validator.controller.impl.command.parse;

import by.training.xml_validator.bean.ParserName;
import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.controller.ParseCommand;
import by.training.xml_validator.dao.FileXML;
import by.training.xml_validator.dao.factory.DAOFactory;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.exception.ServiceException;
import by.training.xml_validator.service.factory.ParserFactory;
import by.training.xml_validator.util.ArgumentParser;
import by.training.xml_validator.util.exception.UtilException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseViaSAX implements ParseCommand {
    private static final Logger log = Logger.getLogger(ParseViaSAX.class.getName());

    @Override
    public WebXML parse(String request) {
        WebXML webXML = null;
        String filePath;
        try {
            filePath = ArgumentParser.getFilePath(request);

            ParserFactory factory = ParserFactory.getInstance();
            Parser parser = factory.getParser(ParserName.SAX);

            DAOFactory daoFactory = DAOFactory.getInstance();
            FileXML fileXML = daoFactory.getFileXML();

            fileXML.setFile(filePath);

            webXML = parser.parse(fileXML);
        } catch (ServiceException | UtilException e) {
            log.log(Level.SEVERE, "Error while parsing via SAX", e);
        }
        return webXML;
    }
}

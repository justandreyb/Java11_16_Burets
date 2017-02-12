package by.training.xml_validator.controller.impl.command.parse;

import by.training.xml_validator.bean.WebXML;
import by.training.xml_validator.controller.ParseCommand;

public class WrongParseCommand implements ParseCommand {
    @Override
    public WebXML parse(String request) {
        return null;
    }
}

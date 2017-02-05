package by.training.xml_validator.controller.impl.command;

import by.training.xml_validator.bean.ParserName;
import by.training.xml_validator.controller.Command;

public class WrongCommand implements Command {
    @Override
    public String validate(String request) {
        return "Wrong command";
    }

    @Override
    public void setParserName(ParserName parserName) {

    }
}

package by.training.xml_validator.controller.impl.command.validate;

import by.training.xml_validator.bean.ParserName;
import by.training.xml_validator.controller.ValidateCommand;

public class WrongValidateCommand implements ValidateCommand {
    @Override
    public String validate(String request) {
        return "Wrong command";
    }

    @Override
    public void setParserName(ParserName parserName) {

    }
}

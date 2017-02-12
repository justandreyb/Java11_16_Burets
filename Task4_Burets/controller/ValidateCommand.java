package by.training.xml_validator.controller;

import by.training.xml_validator.bean.ParserName;

public interface ValidateCommand {
    String validate(String request);
    void setParserName(ParserName parserName);
}

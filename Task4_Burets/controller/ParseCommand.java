package by.training.xml_validator.controller;

import by.training.xml_validator.bean.WebXML;

public interface ParseCommand {
    WebXML parse(String request);
}

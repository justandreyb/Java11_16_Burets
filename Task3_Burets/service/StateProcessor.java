package by.training.xml_analyzer.service;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.util.CharacterStream;

public interface StateProcessor {
    Node performAnalyze(CharacterStream stream, char element) throws ServiceException;
}

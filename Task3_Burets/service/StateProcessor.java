package by.training.xml_analyzer.service;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.dao.impl.FileCharacterStreamImpl;

public interface StateProcessor {
    Node performAnalyze(FileCharacterStreamImpl stream, char element) throws ServiceException;
}

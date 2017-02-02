package by.training.xml_analyzer.service.impl;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.dao.CharacterStream;
import by.training.xml_analyzer.service.StateProcessor;
import by.training.xml_analyzer.dao.impl.FileCharacterStreamImpl;

public class WrongStateProcessor implements StateProcessor {
    /*
        If undefined symbol received, it will be the wrong state.
    */
    @Override
    public Node performAnalyze(CharacterStream stream, char element) throws ServiceException {
        return null;
    }
}

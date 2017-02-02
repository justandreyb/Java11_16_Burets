package by.training.xml_analyzer.service.impl;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.AnalyzedElement;
import by.training.xml_analyzer.bean.ElementType;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.bean.NodeType;
import by.training.xml_analyzer.dao.CharacterStream;
import by.training.xml_analyzer.service.StateProcessor;
import by.training.xml_analyzer.util.CharactersBlockAnalyzer;

import java.util.ArrayList;

public class QuestionStateProcessor implements StateProcessor {
    /*
        If question symbol received, it will be the standard xml tag.
            <?xml version="1.0" encoding="UTF-8"?>
    */
    @Override
    public Node performAnalyze(CharacterStream stream, char element) throws ServiceException {
        Node standardTag = new Node();
        standardTag.setType(NodeType.XML_INFO);

        CharactersBlockProcessor charactersProcessor = new CharactersBlockProcessor();
        String currentBlock = charactersProcessor.createString(stream, element);

        ArrayList<AnalyzedElement> elements;
        elements = CharactersBlockAnalyzer.getInsideTagBlockInfo(currentBlock);

        for (AnalyzedElement currentElement : elements) {
            if (currentElement.getType().equals(ElementType.ARGUMENT)) {
                standardTag.addArguments(currentElement.getText());
            }
        }

        return standardTag;
    }
}

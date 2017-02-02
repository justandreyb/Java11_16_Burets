package by.training.xml_analyzer.service.impl;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.AnalyzedElement;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.bean.NodeType;
import by.training.xml_analyzer.dao.CharacterStream;
import by.training.xml_analyzer.service.StateProcessor;
import by.training.xml_analyzer.util.CharactersBlockAnalyzer;

import java.util.ArrayList;

public class LetterStateProcessor implements StateProcessor {
    /*
        If letter symbol received, it will be normal or single tag.
        Example :
            <city id=1 name="Minsk">
        or
            <website link="http://www.google.com" />
    */
    @Override
    public Node performAnalyze(CharacterStream stream, char element) throws ServiceException {
        Node normalTag = new Node();

        CharactersBlockProcessor charactersProcessor = new CharactersBlockProcessor();
        String currentBlock = charactersProcessor.createString(stream, element);

        ArrayList<AnalyzedElement> elements;
        elements = CharactersBlockAnalyzer.getInsideTagBlockInfo(currentBlock);

        normalTag.setType(NodeType.OPEN_TAG);
        for (AnalyzedElement currentElement : elements) {
            switch (currentElement.getType()) {
                case NAME:
                    normalTag.setName(currentElement.getText());
                    break;
                case ARGUMENT:
                    normalTag.addArguments(currentElement.getText());
                    break;
                case CLOSED_SYMBOL:
                    normalTag.setType(NodeType.SINGLE_TAG);
                    break;
                default:
                    break;
            }
        }

        return normalTag;
    }
}

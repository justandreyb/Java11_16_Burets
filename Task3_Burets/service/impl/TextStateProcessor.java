package by.training.xml_analyzer.service.impl;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.bean.NodeType;
import by.training.xml_analyzer.service.StateProcessor;
import by.training.xml_analyzer.dao.impl.FileCharacterStreamImpl;

public class TextStateProcessor implements StateProcessor {
    @Override
    public Node performAnalyze(FileCharacterStreamImpl stream, char element) throws ServiceException {
        Node textBlock = new Node();
        textBlock.setType(NodeType.TEXT_BLOCK);

        CharactersBlockProcessor charactersProcessor = new CharactersBlockProcessor();
        String currentBlock = charactersProcessor.createString(stream, element);
        currentBlock = currentBlock.substring(0, currentBlock.length() - 1);

        textBlock.setText(currentBlock);

        return textBlock;
    }
}

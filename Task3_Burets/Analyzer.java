package by.training.xml_analyzer;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.bean.NodeType;
import by.training.xml_analyzer.bean.NodesSet;
import by.training.xml_analyzer.dao.CharacterStream;
import by.training.xml_analyzer.dao.exception.DAOException;
import by.training.xml_analyzer.exception.AnalyzerException;
import by.training.xml_analyzer.service.StateProcessor;
import by.training.xml_analyzer.service.factory.StateProcessorsProvider;
import by.training.xml_analyzer.util.CharacterAnalyzer;
import by.training.xml_analyzer.dao.factory.CharacterStreamFactory;

import java.util.ArrayList;

public class Analyzer {
    private String filePath;

    public Analyzer() {}

    public void setFile(String filePath) {
        this.filePath = filePath;
    }

    public NodesSet analyze() throws AnalyzerException {
        NodesSet set = null;

        if (!isValidFilePath(filePath)) {
            throw new AnalyzerException("Wrong path to xml file");
        }

        CharacterStreamFactory characterStreamFactory = CharacterStreamFactory.getInstance();
        CharacterStream stream = characterStreamFactory.getFileCharacterStream();
        try {
            stream.setFilePath(filePath);

            StateProcessorsProvider stateProcessor = StateProcessorsProvider.getInstance();
            StateProcessor processor;

            ArrayList<Node> nodes = new ArrayList<Node>(0);
            int currentChar;
            boolean afterTextBlock = false;
            do {
                currentChar = stream.getNext();
                if (!CharacterAnalyzer.isControlCharacter((char) currentChar)) {

                    if (afterTextBlock || (char) currentChar == '<') {
                        if (!afterTextBlock) {
                            currentChar = stream.getNext();
                        }
                        afterTextBlock = false;

                        processor = stateProcessor.getProcessor((char) currentChar);
                    } else if (CharacterAnalyzer.isLetter((char) currentChar)) {
                        processor = stateProcessor.getTextStateProcessor();
                    } else {
                        processor = stateProcessor.getWrongStateProcessor();
                    }

                    Node node = processor.performAnalyze(stream, (char) currentChar);
                    if (node != null) {
                        nodes.add(node);
                        if (node.getType().equals(NodeType.TEXT_BLOCK)) {
                            afterTextBlock = true;
                        }
                    }
                }
            } while(currentChar != -1);

            set = new NodesSet(nodes);
        } catch (ServiceException | DAOException e) {
            throw new AnalyzerException(e.getMessage());
        } finally {
            try {
                stream.closeStream();
            } catch (DAOException e) {
                throw new AnalyzerException(e.getMessage());
            }
        }
        return set;
    }

    private boolean isValidFilePath(String filePath) {
        return filePath != null && !filePath.isEmpty();
    }
}

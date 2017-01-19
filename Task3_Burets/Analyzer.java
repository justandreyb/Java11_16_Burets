package by.training.xml_analyzer;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.bean.NodeType;
import by.training.xml_analyzer.bean.NodesSet;
import by.training.xml_analyzer.exception.AnalyzerException;
import by.training.xml_analyzer.service.StateProcessor;
import by.training.xml_analyzer.service.StateProcessorsProvider;
import by.training.xml_analyzer.util.CharacterAnalyzer;
import by.training.xml_analyzer.util.CharacterStream;
import by.training.xml_analyzer.util.exception.UtilException;

import java.util.ArrayList;

public class Analyzer {
    private String filePath;

    public Analyzer() {}

    public void setFile(String filePath) {
        this.filePath = filePath;
    }

    //TODO: Debug
    public NodesSet analyze() throws AnalyzerException {
        NodesSet set = null;
        if (filePath != null && !filePath.isEmpty()){
            CharacterStream stream = new CharacterStream();
            try {
                stream.setFilePath(filePath);

                StateProcessorsProvider stateProcessor = StateProcessorsProvider.getInstance();
                StateProcessor processor;

                ArrayList<Node> nodes = new ArrayList<>(0);
                int currentChar;
                boolean afterTextBlock = false;
                do {
                    currentChar = stream.getNext();
                    if (currentChar != -1) {
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
                    }
                } while(currentChar != -1);

                set = new NodesSet(nodes);
            } catch (UtilException | ServiceException e) {
                throw new AnalyzerException(e.getMessage());
            } finally {
                try {
                    stream.closeStream();
                } catch (UtilException e) {
                    throw new AnalyzerException(e.getMessage());
                }
            }
        } else {
            throw new AnalyzerException("Wrong path to xml file");
        }
        return set;
    }
}

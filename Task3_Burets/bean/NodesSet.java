package by.training.xml_analyzer.bean;

import by.training.xml_analyzer.exception.AnalyzerException;

import java.util.ArrayList;

public class NodesSet {

    private ArrayList<Node> nodes;
    private int currentElement;

    public NodesSet() {
        currentElement = 0;
        nodes = new ArrayList<>(0);
    }

    public NodesSet(ArrayList<Node> nodes) {
        currentElement = 0;
        this.nodes = nodes;
    }

    public boolean hasNext() {
        int currentLength = nodes.size();
        return (currentLength - currentElement) > 1;
    }

    public Node next() throws AnalyzerException {
        Node node = null;
        if (this.hasNext()) {
            node = nodes.get(currentElement + 1);
            currentElement = currentElement + 1;
        }

        return node;
    }
}

package by.training.xml_analyzer.bean;

import by.training.xml_analyzer.controller.exception.AnalyzerException;

import java.util.ArrayList;

public class NodesSet {

    private ArrayList<Node> nodes;
    private int currentElement;

    public NodesSet() {
        currentElement = 0;
        nodes = new ArrayList<Node>(0);
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }

        NodesSet set = (NodesSet) o;

        if (this.currentElement != set.currentElement) {
            return false;
        }
        return this.nodes.equals(set.nodes);
    }
    
    @Override
    public int hashCode() {
        int result = currentElement;
        result = 31 * result + nodes.hashCode();
        return result;
    }
}

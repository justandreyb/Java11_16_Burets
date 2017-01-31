package by.training.xml_analyzer.bean;

import java.util.ArrayList;

public class Node {
    private String name;
    private String text;
    private ArrayList<String> arguments;
    private NodeType type;

    public Node() {
        arguments = new ArrayList<>(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public void addArguments(String argument) {
        this.arguments.add(argument);
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
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

        Node node = (Node) o;

        if (!this.name.equals(node.name)) {
            return false;
        }
        if (!this.text.equals(node.text)) {
            return false;
        }
        if (!arguments.equals(node.arguments)) {
            return false;
        }
        return this.type == node.type;
    }
    
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + arguments.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}

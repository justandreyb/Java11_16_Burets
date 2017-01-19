package by.training.xml_analyzer.bean;

public class AnalyzedElement {
    private ElementType type;
    private String text;

    public AnalyzedElement() {}

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

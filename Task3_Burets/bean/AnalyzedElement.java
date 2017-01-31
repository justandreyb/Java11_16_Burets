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

        AnalyzedElement element = (AnalyzedElement) o;

        if (!this.text.equals(element.text)) {
            return false;
        }
        return this.type == element.type;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}

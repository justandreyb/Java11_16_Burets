package by.training.xml_analyzer;

import by.training.xml_analyzer.controller.Analyzer;
import by.training.xml_analyzer.controller.impl.AnalyzerImpl;

public class AnalyzerFactory {
    private final static AnalyzerFactory instance = new AnalyzerFactory();

    private AnalyzerFactory() {}

    public static AnalyzerFactory getInstance() {
        return instance;
    }

    public Analyzer newAnalyzer() {
        return new AnalyzerImpl();
    }
}

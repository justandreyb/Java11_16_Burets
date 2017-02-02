package by.training.xml_analyzer.controller;

import by.training.xml_analyzer.bean.NodesSet;
import by.training.xml_analyzer.controller.exception.AnalyzerException;

public interface Analyzer {
    void setFile(String filePath);
    NodesSet analyze() throws AnalyzerException;
}

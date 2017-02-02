package by.training.xml_analyzer.example;

import by.training.xml_analyzer.AnalyzerFactory;
import by.training.xml_analyzer.controller.Analyzer;
import by.training.xml_analyzer.controller.impl.AnalyzerImpl;
import by.training.xml_analyzer.bean.Node;
import by.training.xml_analyzer.bean.NodesSet;
import by.training.xml_analyzer.controller.exception.AnalyzerException;

public class AnalyzerTest {

    public static void main(String[] args) {
        AnalyzerFactory factory = AnalyzerFactory.getInstance();
        Analyzer analyzer = factory.newAnalyzer();

        NodesSet set = null;
        analyzer.setFile("/home/justandreyb/testXML.xml");
        try {
            set = analyzer.analyze();


        if (set != null) {
            int depth = 0;
            while (set.hasNext()) {
                Node node = set.next();

                switch (node.getType()) {
                    case OPEN_TAG:
                        System.out.println(getTabs(depth) + node.getType() + "  " + node.getName());
                        if (node.getArguments() != null) {
                            for (String argument : node.getArguments()) {
                                System.out.println(getTabs(depth + 2) + argument);
                            }
                        }
                        depth++;
                        break;
                    case SINGLE_TAG:
                        System.out.println(getTabs(depth) + node.getType() + "  " + node.getName());
                        break;
                    case CLOSED_TAG:
                        depth--;
                        System.out.println(getTabs(depth) + node.getType() + "  " + node.getName());
                        break;
                    case TEXT_BLOCK:
                        System.out.println(getTabs(depth) + node.getType() + "  " + node.getText());
                        break;
                    default:
                        break;
                }
            }
        }

        } catch (AnalyzerException e) {
            e.printStackTrace();
        }
    }

    private static String getTabs(int depth) {
        String result = "";
        for (int i = 0; i < depth; i++) {
            result = result + "\t";
        }
        return result;
    }
}

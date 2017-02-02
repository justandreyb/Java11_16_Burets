package by.training.xml_analyzer.util;

import by.training.xml_analyzer.bean.AnalyzedElement;
import by.training.xml_analyzer.bean.ElementType;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharactersBlockAnalyzer {

    private final static String ATTRIBUTE_REGEXPR = "[\\w]+ ?= ?(['\\\"]).+?\\1|[\\w]+ ?= ?\\d";

    public static ArrayList<AnalyzedElement> getInsideTagBlockInfo(String charactersBlock) {
        ArrayList<AnalyzedElement> elements = new ArrayList<AnalyzedElement>(0);

        AnalyzedElement name = new AnalyzedElement();
        name.setType(ElementType.NAME);
        name.setText(getTagName(charactersBlock));

        elements.add(name);

        ArrayList<String> attributes = takeAttributes(ATTRIBUTE_REGEXPR, charactersBlock);
        for (String argument : attributes) {
            AnalyzedElement element = new AnalyzedElement();
            element.setType(ElementType.ARGUMENT);
            element.setText(argument);

            elements.add(element);
        }

        if (charactersBlock.contains("/>")) {
            AnalyzedElement element = new AnalyzedElement();
            element.setType(ElementType.CLOSED_SYMBOL);
        }

        return elements;
    }

    private static String getTagName(String tag) {
        String name = "";
        if (tag.contains(" ")) {
            name = tag.substring(0, tag.indexOf(' '));
        } else if (tag.contains(">")) {
            name = tag.substring(0, tag.indexOf('>'));
        }

        if (name.contains("/")) {
            name = name.substring(1, name.length());
        }
        return name;
    }

    private static ArrayList<String> takeAttributes(String regExpr, String text) {
        Pattern pattern = Pattern.compile(regExpr);
        Matcher matcher = pattern.matcher(text);

        ArrayList<String> results = new ArrayList<String>(0);
        while (matcher.find()) {
            results.add(matcher.group());
        }

        return results;
    }
}

package by.training.xml_analyzer.service;

import by.training.xml_analyzer.service.impl.*;
import by.training.xml_analyzer.util.CharacterAnalyzer;

import java.util.HashMap;

public class StateProcessorsProvider {

    private static StateProcessorsProvider stateProcessor = new StateProcessorsProvider();

    private HashMap<Character, StateProcessor> provider = new HashMap<>(0);

    private static StateProcessor textStateProcessor = new TextStateProcessor();

    private StateProcessorsProvider() {
        provider.put('?', new QuestionStateProcessor());
        provider.put('/', new SlashStateProcessor());
    }

    public static StateProcessorsProvider getInstance() {
        return stateProcessor;
    }

    public StateProcessor getTextStateProcessor() {
        return textStateProcessor;
    }

    public StateProcessor getWrongStateProcessor() {
        return new WrongStateProcessor();
    }

    public StateProcessor getProcessor(char element) {
        StateProcessor processor;
        if (CharacterAnalyzer.isLetter(element)) {
            processor = new LetterStateProcessor();
        } else if (provider.keySet().contains(element)) {
            processor = provider.get(element);
        } else {
            processor = new WrongStateProcessor();
        }

        return processor;
    }

}

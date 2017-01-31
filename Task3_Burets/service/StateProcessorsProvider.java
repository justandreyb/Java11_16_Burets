package by.training.xml_analyzer.service;

import by.training.xml_analyzer.service.impl.*;
import by.training.xml_analyzer.util.CharacterAnalyzer;

import java.util.HashMap;

public class StateProcessorsProvider {

    private static final StateProcessorsProvider stateProcessor = new StateProcessorsProvider();

    private HashMap<Character, StateProcessor> provider = new HashMap<>(0);

    private final StateProcessor textStateProcessor = new TextStateProcessor();
    private final StateProcessor wrongStateProcessor = new WrongStateProcessor();

    private StateProcessorsProvider() {
        provider.put('?', new QuestionStateProcessor());
        provider.put('/', new SlashStateProcessor());
    }

    public static StateProcessorsProvider getInstance() {
        return stateProcessor;
    }

    public StateProcessor getTextStateProcessor() {
        return this.textStateProcessor;
    }

    public StateProcessor getWrongStateProcessor() {
        return this.wrongStateProcessor;
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

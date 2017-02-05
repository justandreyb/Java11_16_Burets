package by.training.xml_validator.service.factory;

import by.training.xml_validator.bean.ParserName;
import by.training.xml_validator.service.Parser;
import by.training.xml_validator.service.impl.ParserDOMImpl;
import by.training.xml_validator.service.impl.ParserSAXImpl;
import by.training.xml_validator.service.impl.ParserStAXImpl;
import by.training.xml_validator.service.impl.WrongParser;

import java.util.HashMap;

public class ParserFactory {
    private final static ParserFactory instance = new ParserFactory();

    private HashMap<ParserName, Parser> provider = new HashMap<>(3);

    private ParserFactory() {
        provider.put(ParserName.DOM, new ParserDOMImpl());
        provider.put(ParserName.SAX, new ParserSAXImpl());
        provider.put(ParserName.STAX, new ParserStAXImpl());

        provider.put(ParserName.WRONG, new WrongParser());
    }

    public static ParserFactory getInstance() {
        return instance;
    }

    public Parser getParser(ParserName parserName) {
        if (!provider.containsKey(parserName)) {
            return provider.get(ParserName.WRONG);
        }

        return provider.get(parserName);
    }

}

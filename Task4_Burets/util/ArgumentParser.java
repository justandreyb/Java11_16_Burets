package by.training.xml_validator.util;

import by.training.xml_validator.bean.CommandName;
import by.training.xml_validator.bean.ParserName;
import by.training.xml_validator.util.exception.UtilException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {
    @SuppressWarnings("Duplicates")
    public static ArrayList<String> parse(String line) {
        ArrayList<String> result = new ArrayList<String>(0);
        String regex = "([\\w\\_\\@\\.\\-])+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        while(matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static ParserName getParserName(CommandName name) throws UtilException {
        if (CommandName.WRONG_COMMAND.equals(name)) {
            throw new UtilException("Wrong command");
        }
        String commandName = name.name();
        commandName = commandName.replaceFirst("VALIDATE_VIA_", "");

        ParserName parserName;
        try {
            parserName = ParserName.valueOf(commandName);
        } catch (IllegalArgumentException e) {
            throw new UtilException(e);
        }

        return parserName;
    }

    public static String getFilePath(String request) throws UtilException {
        if (request.isEmpty() || !request.contains(" ")) {
            throw new UtilException("Wrong amount of args");
        }

        request = request.substring(request.indexOf(" ") + 1, request.length());
        if (request.isEmpty()) {
            throw new UtilException("Wrong amount of args");
        }

        return request;
    }
}

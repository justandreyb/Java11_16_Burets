package by.training.Task2_Burets.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {
    public static ArrayList<String> parse(String line) {
        ArrayList<String> result = new ArrayList<>(0);
        String regex = "([\\w\\_\\@\\.\\-])+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        while(matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }
}

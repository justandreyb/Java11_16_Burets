package by.training.xml_analyzer.util;

public class CharacterAnalyzer {
    public static boolean isLetter(char element) {
        return (element >= 'A' && element <= 'z') || (element >= '0' && element <= '9');
    }

    public static boolean isControlCharacter(char element) {
        return element == '\n' || element == '\t' || element == ' ';
    }
}

package by.training.xml_analyzer.dao.factory;

public class CharacterStreamFactory {

    public static final CharacterStreamFactory factory = new CharacterStreamFactory();

    private CharacterStreamFactory() {}

    private final CharacterStream fileCharacterStream = new FileCharacterStreamImpl(); 

    public static CharacterStreamFactory getInstance() {
        return factory;
    }

    public CharacterStream getFileCharacterStream() {
    	return this.fileCharacterStream;
    }
}

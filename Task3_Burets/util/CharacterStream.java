package by.training.xml_analyzer.util;

import by.training.xml_analyzer.util.exception.UtilException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CharacterStream {

    private FileInputStream fileInputStream;

    public CharacterStream() {}

    public void setFilePath(String filePath) throws UtilException {
        try {
            this.fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new UtilException(e.getMessage());
        }
    }

    public int getNext() throws UtilException {
        try {
            return fileInputStream.read();
        }
        catch(IOException ex){
            throw new UtilException(ex.getMessage());
        }
    }

    public void closeStream() throws UtilException {
        try {
            this.fileInputStream.close();
        } catch (IOException e) {
            throw new UtilException(e.getMessage());
        }
    }

    //TODO: Mb it's DAO layer?
}

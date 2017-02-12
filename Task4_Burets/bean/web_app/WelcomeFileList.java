package by.training.xml_validator.bean.web_app;

import java.util.ArrayList;
import java.util.List;

public class WelcomeFileList {
    private List<String> welcomeFiles;

    public WelcomeFileList() {
        this.welcomeFiles = new ArrayList<String>();
    }

    public void addWelcomeFile(String welcomeFile) {
        this.welcomeFiles.add(welcomeFile);
    }
    public List<String> getWelcomeFiles() {
        return welcomeFiles;
    }

    @Override
    public String toString() {
        return null;
    }
}

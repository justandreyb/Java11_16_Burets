package by.training.xml_validator.bean;

import by.training.xml_validator.bean.web_app.WebApp;

public class WebXML {
    private WebApp webApp;
    private String id;
    private String version;

    public WebXML() {
        webApp = new WebApp();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setWebApp(WebApp webApp) {
        this.webApp = webApp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("id = ");
        builder.append(id);
        builder.append("\n");
        builder.append("version = ");
        builder.append(version);
        builder.append("\n");
        builder.append(webApp.toString());

        return builder.toString();
    }

    public WebApp getWebApp() {
        return webApp;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }
}
    

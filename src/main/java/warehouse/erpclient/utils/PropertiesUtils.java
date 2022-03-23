package warehouse.erpclient.utils;

import javafx.application.Platform;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static warehouse.erpclient.utils.AlertUtils.createExceptionAlert;

public class PropertiesUtils {

    public static String createUrl(String endpointPath){
        return getServerUrl() + endpointPath;
    }

    private static String getServerUrl(){
        String serverUrl="";
        try{
            String resourcePath = Thread.currentThread().getContextClassLoader().getResource("application.properties").getPath();
            Properties properties = new Properties();
            properties.load(new FileInputStream(resourcePath));
            return properties.getProperty("server.url");
        } catch (IOException exception) {
            Platform.runLater(() -> createExceptionAlert(exception.getMessage()));
        }
        return serverUrl;
    }
}

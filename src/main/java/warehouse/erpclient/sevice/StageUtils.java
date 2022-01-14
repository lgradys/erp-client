package warehouse.erpclient.sevice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import warehouse.erpclient.AppStarter;

import java.io.IOException;

import static warehouse.erpclient.sevice.AlertUtils.createExceptionAlert;

public class StageUtils {

    public static void stageCreator(Stage stage, String fxmlFilePath) {
        try{
            stage.setScene(sceneCreator(fxmlFilePath));
            stage.show();
        } catch (IOException exception) {
            createExceptionAlert("Something went wrong. Check file access and try again!").show();
        } catch (Exception exception) {
            createExceptionAlert(exception.getMessage()).show();
        }
    }

    public static Scene sceneCreator(String fxmlFilePath) throws IOException {
        Pane sceneMainPane = layoutsLoader(fxmlFilePath);
        return new Scene(sceneMainPane);
    }


    public static Pane layoutsLoader(String fxmlFilePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource(fxmlFilePath));
        return fxmlLoader.load();
    }

}

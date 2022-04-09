package warehouse.erpclient.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import warehouse.erpclient.AppStarter;

import java.io.IOException;

import static warehouse.erpclient.utils.AlertUtils.createAlert;

public class StageUtils {

    public static void stageCreator(Stage stage, String fxmlFilePath) {
        try {
            stage.setScene(sceneCreator(fxmlFilePath));
            stage.show();
        } catch (IOException exception) {
            createAlert("Something went wrong. Check file access and try again!", Alert.AlertType.ERROR).show();
        } catch (Exception exception) {
            createAlert(exception.getMessage(), Alert.AlertType.ERROR).show();
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

    public static void centerPane(Pane loadedPane) {
        AnchorPane.setBottomAnchor(loadedPane, 0.0);
        AnchorPane.setTopAnchor(loadedPane, 0.0);
        AnchorPane.setLeftAnchor(loadedPane, 0.0);
        AnchorPane.setRightAnchor(loadedPane, 0.0);
    }

}

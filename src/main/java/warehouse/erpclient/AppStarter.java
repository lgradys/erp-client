package warehouse.erpclient;

import javafx.application.Application;
import javafx.stage.Stage;

import static warehouse.erpclient.utils.StageUtils.stageCreator;

public class AppStarter extends Application {

    @Override
    public void start(Stage stage) {
        stageCreator(stage,"/warehouse/erpclient/login.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
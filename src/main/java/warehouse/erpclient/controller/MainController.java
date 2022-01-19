package warehouse.erpclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import warehouse.erpclient.dao.ExecutorServiceProvider;

import java.util.concurrent.ExecutorService;

public class MainController {
    
    private final ExecutorService executorService;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane mainPane;

    @FXML
    private Button settingsButton;

    @FXML
    private Button userButton;

    @FXML
    private Button warehouseButton;

    public MainController() {
        executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
    }


//    private void initializeMenuItems() {
//        close.setOnAction(actionEvent -> closeApp());
//        alwaysOnTop.setOnAction(actionEvent -> getStage().setAlwaysOnTop(alwaysOnTop.isSelected()));
//        about.setOnAction(actionEvent -> createInformationAlert("Warehouse management client ...").show());
//    }

    public void closeApp() {
        getStage().close();
    }

    public Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }


}
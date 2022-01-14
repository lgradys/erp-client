package warehouse.erpclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import warehouse.erpclient.dao.ExecutorServiceProvider;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import static warehouse.erpclient.sevice.AlertUtils.createInformationAlert;


public class MainController implements Initializable {

    private final ExecutorService executorService;

    @FXML
    private MenuItem about;

    @FXML
    private CheckMenuItem alwaysOnTop;

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuItem close;

    @FXML
    private Pane mainPane;

    public MainController() {
        executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        close.setOnAction(actionEvent -> closeApp());
        alwaysOnTop.setOnAction(actionEvent -> getStage().setAlwaysOnTop(alwaysOnTop.isSelected()));
        about.setOnAction(actionEvent -> createInformationAlert("Warehouse management client ...").show());
    }

    public void closeApp() {
        executorService.shutdownNow();
        getStage().close();
    }

    public Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }


}
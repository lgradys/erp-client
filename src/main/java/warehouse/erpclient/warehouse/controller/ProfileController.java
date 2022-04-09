package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import warehouse.erpclient.AppStarter;

import java.net.URL;
import java.util.ResourceBundle;

import static warehouse.erpclient.utils.AlertUtils.createAlert;
import static warehouse.erpclient.utils.StageUtils.centerPane;

public class ProfileController implements AppController {

    private final String PROFILE_DATA_FXML_PATH = "/warehouse/erpclient/warehouse/profile_data.fxml";
    private final MainController mainController;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button dataButton;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button settingsButton;

    public ProfileController(MainController mainController) {
        this.mainController = mainController;
    }

    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultView();
        initializeDataButton();
    }

    private void initializeDefaultView() {
        initializeProfileView(PROFILE_DATA_FXML_PATH, mainPane);
    }

    private void initializeDataButton() {
        dataButton.setOnAction(actionEvent -> initializeProfileView(PROFILE_DATA_FXML_PATH, mainPane));
    }

    private void initializeProfileView(String viewPath, Pane mainPane) {
        try {
            mainPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(AppStarter.class.getResource(viewPath));
            Pane loadedPane = loader.load();
            ProfileDataController profileDataController = loader.getController();
            profileDataController.setUsername(mainController.getUserDTO().getUsername());
            profileDataController.setAuthorities(mainController.getUserDTO().getRole());
            profileDataController.setToken(mainController.getAuthorizationToken());
            centerPane(loadedPane);
            mainPane.getChildren().add(loadedPane);
        } catch (Exception e) {
            createAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}

package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import warehouse.erpclient.AppStarter;
import warehouse.erpclient.login.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static warehouse.erpclient.utils.AlertUtils.createAlert;
import static warehouse.erpclient.utils.StageUtils.centerPane;
import static warehouse.erpclient.utils.StageUtils.stageCreator;

public class MainController implements Initializable {

    private final String PROFILE_FXML_PATH = "/warehouse/erpclient/warehouse/profile.fxml";
    private final String WAREHOUSE_FXML_PATH = "/warehouse/erpclient/warehouse/warehouse.fxml";
    private final String LOGIN_FXML = "/warehouse/erpclient/login.fxml";

    private UserDTO userDTO;
    private String authorizationToken;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button profileButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button warehouseButton;

    public MainController(UserDTO userDTO, String authorizationToken) {
        this.userDTO = userDTO;
        this.authorizationToken = authorizationToken;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultView();
        initializeProfileButtons();
        initializeWarehouseButton();
        initializeLogoutButton();
    }

    private void initializeDefaultView() {
        initializeMainView(WAREHOUSE_FXML_PATH, mainPane, new WarehouseController(this));
    }


    public void initializeLogoutButton() {
        logoutButton.setOnAction(actionEvent -> {
            getStage().close();
            stageCreator(new Stage(), LOGIN_FXML);
        });
    }

    public Stage getStage() {
        return (Stage) borderPane.getScene().getWindow();
    }

    public void initializeWarehouseButton() {
        warehouseButton.setOnAction(actionEvent -> initializeMainView(WAREHOUSE_FXML_PATH, mainPane, new WarehouseController(this)));
    }

    public void initializeProfileButtons() {
        profileButton.setOnAction(actionEvent -> initializeMainView(PROFILE_FXML_PATH, mainPane, new ProfileController(this)));
    }

    public void initializeMainView(String viewPath, Pane mainPane, AppController appController) {
        try {
            mainPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(AppStarter.class.getResource(viewPath));
            loader.setController(appController);
            Pane loadedPane = loader.load();
            centerPane(loadedPane);
            mainPane.getChildren().add(loadedPane);
        } catch (IOException e) {
            createAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}

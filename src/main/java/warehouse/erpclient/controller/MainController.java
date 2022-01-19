package warehouse.erpclient.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import warehouse.erpclient.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static warehouse.erpclient.sevice.AlertUtils.createExceptionAlert;
import static warehouse.erpclient.sevice.StageUtils.layoutsLoader;
import static warehouse.erpclient.sevice.StageUtils.stageCreator;

public class MainController implements Initializable {

    private final String PROFILE_FXML_PATH = "/warehouse/erpclient/warehouse/profile.fxml";
    private final String WAREHOUSE_FXML_PATH = "/warehouse/erpclient/warehouse/warehouse.fxml";
    private final String LOGIN_FXML = "/warehouse/erpclient/login.fxml";

    private UserDTO userDTO;
    private String authenticationToken;

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


    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultView();
        initializeProfileButtons();
        initializeWarehouseButton();
        initializeLogoutButton();
    }

    private void initializeDefaultView() {
        initializeView(PROFILE_FXML_PATH);
    }

    private void initializeLogoutButton() {
        logoutButton.setOnAction(actionEvent -> {
            getStage().close();
            stageCreator(new Stage(), LOGIN_FXML);
        });
    }

    private Stage getStage() {
        return (Stage) borderPane.getScene().getWindow();
    }

    private void initializeWarehouseButton() {
        warehouseButton.setOnAction(actionEvent -> initializeView(WAREHOUSE_FXML_PATH));
    }

    private void initializeProfileButtons() {
        profileButton.setOnAction(actionEvent -> initializeView(PROFILE_FXML_PATH));
    }

    private void initializeView(String viewPath) {
        try {
            mainPane.getChildren().clear();
            Pane loadedPane = layoutsLoader(viewPath);
            centerPane(loadedPane);
            mainPane.getChildren().add(loadedPane);
        } catch (IOException e) {
            createExceptionAlert(e.getMessage());
        }
    }

    private void centerPane(Pane loadedPane) {
        AnchorPane.setBottomAnchor(loadedPane, 0.0);
        AnchorPane.setTopAnchor(loadedPane, 0.0);
        AnchorPane.setLeftAnchor(loadedPane, 0.0);
        AnchorPane.setRightAnchor(loadedPane, 0.0);
    }

}

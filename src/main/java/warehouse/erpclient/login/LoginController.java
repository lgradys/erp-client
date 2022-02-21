package warehouse.erpclient.login;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import warehouse.erpclient.AppStarter;
import warehouse.erpclient.login.dto.LoginCredentials;
import warehouse.erpclient.login.dto.UserDTO;
import warehouse.erpclient.utils.dao.ExecutorServiceProvider;
import warehouse.erpclient.utils.dto.Error;
import warehouse.erpclient.utils.dto.RequestResult;
import warehouse.erpclient.warehouse.controller.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static warehouse.erpclient.utils.AlertUtils.createExceptionAlert;

public class LoginController implements Initializable {

    private final String MAIN_FXML_PATH = "/warehouse/erpclient/main.fxml";

    private final ExecutorService executorService;
    private final LoginClient loginClient;

    @FXML
    private Button closeButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private HBox mainPane;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameFiled;

    @FXML
    private ProgressBar progressBar;

    public LoginController() {
        executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
        loginClient = new LoginClient();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBinds(usernameFiled, passwordField);
        initializeListeners(usernameFiled, passwordField);
        initializeCloseButton();
        initializeLoginButton();
    }

    private void initializeListeners(TextField ... textFields) {
        Arrays.stream(textFields).forEach(textField -> textField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.isEmpty()) errorLabel.setText("");
        }));
    }

    private void initializeBinds(TextField ... textFields) {
        BooleanBinding booleanBinding = Arrays.stream(textFields).map(textField -> textField.textProperty().isEmpty())
                .reduce(BooleanExpression::or)
                .orElse(getFalseBooleanBinding());
        loginButton.disableProperty().bind(booleanBinding);
    }

    private BooleanBinding getFalseBooleanBinding() {
        return new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return false;
            }
        };
    }

    private void initializeLoginButton() {
        loginButton.setOnAction(actionEvent -> {
            progressBar.setVisible(true);
            LoginCredentials loginCredentials = new LoginCredentials(usernameFiled.getText(), passwordField.getText());
            authenticate(loginCredentials);
        });
    }

    private void authenticate(LoginCredentials loginCredentials) {
        clearLoginForm(usernameFiled, passwordField);
        executorService.submit(() -> loginClient.processAuthentication(loginCredentials, responseEntity -> Platform.runLater(() -> {
            progressBar.setVisible(false);
            handleAuthenticationResult(responseEntity);
        })));
    }

    private void clearLoginForm(TextField ... textFields) {
        Arrays.stream(textFields).forEach(TextInputControl::clear);
    }

    private void handleAuthenticationResult(ResponseEntity<RequestResult<UserDTO>> responseEntity) {
        HttpStatus responseStatus = responseEntity.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            UserDTO userDTO = responseEntity.getBody().getResource().stream().findAny().get();
            System.out.println(userDTO);
            String authorizationToken = "";
            List<String> authorizationHeaders = responseEntity.getHeaders().get("Authorization");
            if (authorizationHeaders != null) authorizationToken = authorizationHeaders.stream().findAny().orElse("");
            getStage().close();
            openMainStage(userDTO, authorizationToken);
        }
        if (responseStatus.is4xxClientError() || responseStatus.is5xxServerError()) {
            String errors = responseEntity.getBody().getError().stream()
                    .map(Error::getMessage)
                    .collect(Collectors.joining(" "));
            errorLabel.setText(errors);
        }
    }

    private void openMainStage(UserDTO userDTO, String authorizationToken) {
        try {
            FXMLLoader loader = new FXMLLoader(AppStarter.class.getResource(MAIN_FXML_PATH));
            loader.setController(new MainController(userDTO, authorizationToken));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            createExceptionAlert(e.getMessage());
        }
    }

    private void initializeCloseButton() {
        closeButton.setOnAction(actionEvent -> closeApp());
    }

    public void closeApp() {
        getStage().close();
    }

    public Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }

}

package warehouse.erpclient.controller;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import warehouse.erpclient.dao.ExecutorServiceProvider;
import warehouse.erpclient.dto.LoginCredentials;
import warehouse.erpclient.dto.UserDTO;
import warehouse.erpclient.rest.LoginClient;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class LoginController implements Initializable {

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
            LoginCredentials loginCredentials = LoginCredentials.builder()
                    .username(usernameFiled.getText())
                    .password(passwordField.getText())
                    .build();
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

    private void handleAuthenticationResult(ResponseEntity<?> responseEntity) {
        HttpStatus responseStatus = responseEntity.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            UserDTO user = (UserDTO) responseEntity.getBody();
            String authenticationToken = "";
            List<String> authorizationHeaders = responseEntity.getHeaders().get("Authorization");
            if (authorizationHeaders != null) authenticationToken = authorizationHeaders.stream().findAny().orElse("");
            //todo open main stage
        }
        if (responseStatus.is4xxClientError()) {
            errorLabel.setText((String) responseEntity.getBody());
        }
        if (responseStatus.is5xxServerError()) {
            errorLabel.setText((String) responseEntity.getBody());
            //todo alert exception;
        }
    }

    private void initializeCloseButton() {
        closeButton.setOnAction(actionEvent -> closeApp());
    }

    public void closeApp() {
        executorService.shutdownNow();
        getStage().close();
    }

    private Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }
}

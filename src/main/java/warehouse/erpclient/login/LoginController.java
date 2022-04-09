package warehouse.erpclient.login;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import warehouse.erpclient.login.dto.LoginCredentials;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final LoginService loginService;

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
        loginService = new LoginService();
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
            loginService.authenticate(loginCredentials, usernameFiled, passwordField, errorLabel, progressBar, mainPane);
        });
    }

    private void initializeCloseButton() {
        closeButton.setOnAction(actionEvent -> loginService.closeApp(mainPane));
    }

}

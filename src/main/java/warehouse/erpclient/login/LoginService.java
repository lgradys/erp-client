package warehouse.erpclient.login;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static warehouse.erpclient.utils.AlertUtils.createAlert;

public class LoginService {

    private final String MAIN_FXML_PATH = "/warehouse/erpclient/main.fxml";

    private final ExecutorService executorService;
    private final LoginClient loginClient;

    public LoginService() {
        executorService = ExecutorServiceProvider.INSTANCE.getExecutorService();
        loginClient = new LoginClient();
    }

    public void authenticate(LoginCredentials loginCredentials, TextField usernameFiled, TextField passwordField,
                             Label errorLabel, ProgressBar progressBar, Pane mainPane) {
        clearLoginForm(usernameFiled, passwordField);
        executorService.submit(() -> loginClient.processAuthentication(loginCredentials, responseEntity -> Platform.runLater(() -> {
            progressBar.setVisible(false);
            handleAuthenticationResult(responseEntity, errorLabel, mainPane);
        })));
    }

    private void clearLoginForm(TextField... textFields) {
        Arrays.stream(textFields).forEach(TextInputControl::clear);
    }

    private void handleAuthenticationResult(ResponseEntity<RequestResult<UserDTO>> responseEntity, Label errorLabel, Pane mainPane) {
        HttpStatus responseStatus = responseEntity.getStatusCode();
        if (responseStatus.equals(HttpStatus.OK)) {
            UserDTO userDTO = responseEntity.getBody().getResource().stream().findAny().get();
            String authorizationToken = "";
            List<String> authorizationHeaders = responseEntity.getHeaders().get("Authorization");
            if (authorizationHeaders != null) authorizationToken = authorizationHeaders.stream().findAny().orElse("");
            getStage(mainPane).close();
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
            createAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void closeApp(Pane mainPane) {
        getStage(mainPane).close();
    }

    public Stage getStage(Pane mainPane) {
        return (Stage) mainPane.getScene().getWindow();
    }

}

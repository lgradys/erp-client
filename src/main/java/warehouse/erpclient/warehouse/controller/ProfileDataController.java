package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileDataController {

    @FXML
    private TextField authoritiesField;

    @FXML
    private TextField tokenField;

    @FXML
    private TextField usernameField;

    public void setUsername(String username) {
        usernameField.setText(username);
    }

    public void setToken(String authenticationToken) {
        tokenField.setText(authenticationToken);
    }

    public void setAuthorities(String roleName) {
        authoritiesField.setText(roleName);
    }

}


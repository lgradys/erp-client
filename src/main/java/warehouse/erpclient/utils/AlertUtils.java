package warehouse.erpclient.utils;

import javafx.scene.control.Alert;

public class AlertUtils {

    public static Alert createAlert(String alertInfo, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(alertInfo);
        return alert;
    }

}

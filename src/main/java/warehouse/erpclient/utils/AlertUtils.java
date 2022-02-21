package warehouse.erpclient.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertUtils {

    public static Alert createInformationAlert(String alertInfo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(alertInfo);
        return alert;
    }

    public static Alert createExceptionAlert(String alertInfo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(alertInfo);
        return alert;
    }

    public static Stage createWaitingAlert(String alertInfo) {
        VBox vBox = getVBox();
        Label label = createLabel(alertInfo);
        ProgressBar progressBar = new ProgressBar(ProgressIndicator.INDETERMINATE_PROGRESS);
        return getStage(vBox, label, progressBar);
    }

    private static Label createLabel(String alertInfo) {
       Label label = new Label(alertInfo);
        label.setPrefHeight(Region.USE_COMPUTED_SIZE);
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        return label;
    }

    private static VBox getVBox() {
        VBox vBox = new VBox();
        vBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        vBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        return vBox;
    }

    private static Stage getStage(VBox vBox, Node... nodes) {
        vBox.getChildren().addAll(nodes);
        Scene scene = new Scene(vBox);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        return stage;
    }

}

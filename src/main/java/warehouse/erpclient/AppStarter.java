package warehouse.erpclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class AppStarter extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("/login.fxml"));
        Parent node =  fxmlLoader.load();
        JMetro jMetro = new JMetro(Style.DARK);
        node.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        Scene scene = new Scene(node);
        jMetro.setScene(scene);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package warehouse.erpclient.controller.warehouse;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import warehouse.erpclient.controller.MainController;
import warehouse.erpclient.rest.WarehouseClient;

import java.net.URL;
import java.util.ResourceBundle;

public class WarehouseController extends MainController {

    private final WarehouseClient warehouseClient;

    @FXML
    private Button addButton;

    @FXML
    private BorderPane borderPain;

    @FXML
    private Button editButton;

    @FXML
    private Pane mainPane;

    @FXML
    private Button viewButton;

    @FXML
    private ComboBox<?> warehouseList;

    public WarehouseController() {
        warehouseClient = new WarehouseClient();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
//        initializeMainPain();
//        initializeAddButton();
//        initializeEditButton();
//        initializeViewButton();
    }
}

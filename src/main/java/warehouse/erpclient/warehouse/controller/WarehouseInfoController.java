package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import warehouse.erpclient.warehouse.model.Warehouse;

import java.net.URL;
import java.util.ResourceBundle;

public class WarehouseInfoController implements Initializable {

    private WarehouseController warehouseController;
    private Warehouse warehouse;

    @FXML
    private Button getItemsButton;

    @FXML
    private Label warehouseAddress;

    @FXML
    private Label warehouseName;

    public WarehouseController getWarehouseController() {
        return warehouseController;
    }

    public Button getGetItemsButton() {
        return getItemsButton;
    }

    public Label getWarehouseAddress() {
        return warehouseAddress;
    }

    public Label getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGetItemsButton();
    }

    public void initializeGetItemsButton() {
        getItemsButton.setOnAction(actionEvent -> warehouseController.initializeItemTable(warehouseController.getItemTable(), warehouse.getId()));
    }

}

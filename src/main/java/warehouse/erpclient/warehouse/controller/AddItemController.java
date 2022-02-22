package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.Warehouse;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController extends ItemCrudController implements Initializable {

    private final WarehouseController warehouseController;
    private Item item;

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField unitField;

    @FXML
    private ComboBox<Warehouse> warehouseComboBox;

    public AddItemController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.item = new Item();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWarehouseList(warehouseController, warehouseComboBox);
        initializeDisabledPropertyBindings(addButton, idField, nameField, quantityField, unitField);
        initializeItemPropertyBindings(item, warehouseComboBox, idField, nameField, quantityField, unitField);
        clearFields(idField, nameField, quantityField, unitField);
        initializeAddButton();
        initializeCloseButton(closeButton, mainPane);
    }

    private void initializeAddButton() {
        addButton.setOnAction(actionEvent -> warehouseController.addItem(item));
    }

}

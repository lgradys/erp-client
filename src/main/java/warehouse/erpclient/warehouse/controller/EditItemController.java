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

public class EditItemController extends  ItemCrudController implements Initializable {

    private final WarehouseController warehouseController;
    private Item item;

    @FXML
    private Button closeButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField idField;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField unitField;

    @FXML
    private ComboBox<Warehouse> warehouseComboBox;

    public EditItemController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.item = warehouseController.getItemTable().getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWarehouseList(warehouseController, warehouseComboBox);
        initializeDisabledPropertyBindings(editButton, idField, nameField, quantityField, unitField);
        initializeItemPropertyBindings(item, warehouseComboBox, idField, nameField, quantityField, unitField);
        initializeEditButton();
        initializeCloseButton(closeButton, mainPane);
    }

    private void initializeEditButton() {
        editButton.setOnAction(actionEvent -> warehouseController.editItem(item));
    }


}

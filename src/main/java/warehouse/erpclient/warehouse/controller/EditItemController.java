package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.Warehouse;
import warehouse.erpclient.warehouse.service.QuantityUnitService;

import java.net.URL;
import java.util.ResourceBundle;

public class EditItemController extends  ItemCrudController implements Initializable {

    private final WarehouseController warehouseController;
    private Item item;
    private final QuantityUnitService quantityUnitService;

    @FXML
    private Button closeButton;

    @FXML
    private Button editButton;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> unitComboBox;

    @FXML
    private ComboBox<Warehouse> warehouseComboBox;

    public EditItemController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.item = warehouseController.getItemTable().getSelectionModel().getSelectedItem();
        quantityUnitService = new QuantityUnitService(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWarehouseList(warehouseController, warehouseComboBox);
        initializeQuantityUnitSymbolList(unitComboBox, warehouseController, quantityUnitService);
        setEditValues(item, warehouseComboBox, nameField, quantityField, unitComboBox);
        initializeDisabledPropertyBindings(editButton, unitComboBox, nameField, quantityField);
        initializeEditButton();
        initializeCloseButton(closeButton, mainPane);
        setIntegerTextFormatter(quantityField);
    }

    private void initializeEditButton() {
        editButton.setOnAction(actionEvent -> {
            setItemValues(item, warehouseComboBox, nameField, quantityField, unitComboBox);
            warehouseController.editItem(item);
        });
    }

}

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

public class AddItemController extends ItemCrudController implements Initializable {

    private final WarehouseController warehouseController;
    private final QuantityUnitService quantityUnitService;
    private Item item;

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;

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

    public AddItemController(WarehouseController warehouseController) {
        this.warehouseController = warehouseController;
        this.item = new Item();
        this.quantityUnitService = new QuantityUnitService(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeWarehouseList(warehouseController, warehouseComboBox);
        initializeDisabledPropertyBindings(addButton, unitComboBox, nameField, quantityField);
        initializeQuantityUnitSymbolList(unitComboBox, warehouseController, quantityUnitService);
        initializeAddButton();
        initializeCloseButton(closeButton, mainPane);
        setIntegerTextFormatter(quantityField);
    }

    private void initializeAddButton() {
        addButton.setOnAction(actionEvent -> {
            setItemValues(item, warehouseComboBox, nameField, quantityField, unitComboBox);
            warehouseController.addItem(item);
        });
    }

}

package warehouse.erpclient.warehouse.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.Warehouse;
import warehouse.erpclient.warehouse.service.ItemService;
import warehouse.erpclient.warehouse.service.WarehouseService;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class WarehouseController implements AppController {

    private final MainController mainController;
    private final WarehouseService warehouseService;
    private final ItemService itemService;

    @FXML
    private Button addButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<Item, Long> idColumn;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, Integer> quantityColumn;

    @FXML
    private TableColumn<Item, String> quantityUnitColumn;

    @FXML
    private AnchorPane warehouseInfo;

    @FXML
    private ComboBox<Warehouse> warehouseComboBox;

    public WarehouseController(MainController mainController) {
        this.mainController = mainController;
        this.warehouseService = new WarehouseService(this);
        this.itemService = new ItemService(this);
    }

    public MainController getMainController() {
        return mainController;
    }

    public TableView<Item> getItemTable() {
        return itemTable;
    }

    public ComboBox<Warehouse> getWarehouseComboBox() {
        return warehouseComboBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBox();
        initializeWarehouseInfo();
        initializeTableView();
        initializeBindings(itemTable, editButton, deleteButton);
        initializeAddButton();
        initializeEditButton();
        initializeDeleteButton();
    }

    private void initializeEditButton() {
        editButton.setOnAction(actionEvent -> itemService.openEditLayout());
    }

    private void initializeAddButton() {
        addButton.setOnAction(actionEvent -> itemService.openAddLayout());
    }

    private void initializeBindings(TableView<Item> itemTable, Button... buttons) {
        Arrays.stream(buttons).forEach(button -> button.disableProperty().bind(itemTable.getSelectionModel().selectedItemProperty().isNull()));
    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction(actionEvent -> itemService.deleteItem(itemTable.getSelectionModel().getSelectedItem()));
    }

    private void initializeTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Item, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        quantityUnitColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("quantityUnit"));
        itemTable.setPlaceholder(new Label("Load item data!"));
    }

    private void initializeDefaultWarehouseInfo() {
        warehouseService.initializeWarehouseInfo(warehouseInfo, warehouseComboBox.getSelectionModel().getSelectedItem(), this);
    }

    private void initializeWarehouseInfo() {
        initializeDefaultWarehouseInfo();
        warehouseComboBox.setOnAction(actionEvent ->
                warehouseService.initializeWarehouseInfo(warehouseInfo, warehouseComboBox.getSelectionModel().getSelectedItem(), this));
    }

    private void initializeComboBox() {
        warehouseService.getWarehousesList(warehouseComboBox, mainController.getAuthorizationToken());
    }

    public void initializeItemTable(TableView<Item> itemTable, long warehouseId) {
        itemService.getItems(itemTable, warehouseId);
    }

    public void editItem(Item item) {
        itemService.editItem(item);
    }

    public void addItem(Item item) {
        itemService.addItem(item);
    }

}

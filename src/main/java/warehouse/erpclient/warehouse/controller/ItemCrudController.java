package warehouse.erpclient.warehouse.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.Warehouse;
import warehouse.erpclient.warehouse.service.QuantityUnitService;

import java.util.Arrays;

public class ItemCrudController {

    void initializeQuantityUnitSymbolList(ComboBox<String> unitComboBox, WarehouseController warehouseController, QuantityUnitService quantityUnitService) {
        quantityUnitService.getQuantityUnitList(unitComboBox, warehouseController.getMainController().getAuthorizationToken());
    }

    private Stage getStage(Pane mainPane) {
        return (Stage) mainPane.getScene().getWindow();
    }

    void initializeWarehouseList(WarehouseController warehouseController, ComboBox<Warehouse> warehouseComboBox) {
        warehouseComboBox.setItems(warehouseController.getWarehouseComboBox().getItems());
        warehouseComboBox.getSelectionModel().select(warehouseController.getWarehouseComboBox().getSelectionModel().getSelectedItem());
    }

    void initializeDisabledPropertyBindings(Button button, ComboBox<String> unitComboBox, TextField ... textFields) {
        BooleanBinding booleanBinding = Arrays.stream(textFields).map(textField -> textField.textProperty().isEmpty())
                .reduce(BooleanExpression::or)
                .orElse(getFalseBooleanBinding());
        button.disableProperty().bind(booleanBinding);
        button.disableProperty().bind(unitComboBox.getSelectionModel().selectedItemProperty().isNull());
    }

    private BooleanBinding getFalseBooleanBinding() {
        return new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return false;
            }
        };
    }

    void setItemValues(Item item, ComboBox<Warehouse> warehouseComboBox,
                                TextField nameField, TextField quantityField, ComboBox<String> unitComboBox) {
        item.setWarehouseId(warehouseComboBox.getSelectionModel().getSelectedItem().getId());
        item.setName(nameField.getText());
        item.setQuantity(Integer.parseInt(quantityField.getText()));
        item.setQuantityUnit(unitComboBox.getSelectionModel().getSelectedItem());
    }

    void setEditValues(Item item, ComboBox<Warehouse> warehouseComboBox,
                                        TextField nameField, TextField quantityField, ComboBox<String> unitComboBox) {
        warehouseComboBox.setValue(warehouseComboBox.getValue());
        nameField.textProperty().setValue(item.getName());
        quantityField.textProperty().setValue(String.valueOf(item.getQuantity()));
        unitComboBox.setValue(item.getQuantityUnit());
    }

    void setIntegerTextFormatter(TextField textField){
        textField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }

    void initializeCloseButton(Button closeButton, Pane mainPane) {
        closeButton.setOnAction(actionEvent -> getStage(mainPane).close());
    }

}

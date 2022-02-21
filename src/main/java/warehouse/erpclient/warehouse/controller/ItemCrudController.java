package warehouse.erpclient.warehouse.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import warehouse.erpclient.warehouse.model.Item;
import warehouse.erpclient.warehouse.model.Warehouse;

import java.util.Arrays;

public class ItemCrudController {

    private Stage getStage(Pane mainPane) {
        return (Stage) mainPane.getScene().getWindow();
    }

    void initializeWarehouseList(WarehouseController warehouseController, ComboBox<Warehouse> warehouseComboBox) {
        warehouseComboBox.setItems(warehouseController.getWarehouseComboBox().getItems());
        warehouseComboBox.getSelectionModel().select(warehouseController.getWarehouseComboBox().getSelectionModel().getSelectedItem());
    }

    void initializeDisabledPropertyBindings(Button button, TextField ... textFields) {
        BooleanBinding booleanBinding = Arrays.stream(textFields).map(textField -> textField.textProperty().isEmpty())
                .reduce(BooleanExpression::or)
                .orElse(getFalseBooleanBinding());
        button.disableProperty().bind(booleanBinding);
    }

    private BooleanBinding getFalseBooleanBinding() {
        return new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return false;
            }
        };
    }

    void initializeItemPropertyBindings(Item item, ComboBox<Warehouse> warehouseComboBox,
                                TextField idField, TextField nameField, TextField quantityField, TextField unitField) {
        initializeWarehousePropertyListener(item, warehouseComboBox);
        idField.textProperty().bindBidirectional(item.idProperty(), new NumberStringConverter());
        nameField.textProperty().bindBidirectional(item.nameProperty());
        quantityField.textProperty().bindBidirectional(item.quantityProperty(), new NumberStringConverter());
        unitField.textProperty().bindBidirectional(item.quantityUnitProperty());
    }

    private void initializeWarehousePropertyListener(Item item, ComboBox<Warehouse> warehouseComboBox) {
        item.setWarehouseId(warehouseComboBox.getSelectionModel().getSelectedItem().getId());
        warehouseComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, warehouse, t1) -> {
            item.setWarehouseId(t1.getId());
        });
    }

    void initializeCloseButton(Button closeButton, Pane mainPane) {
        closeButton.setOnAction(actionEvent -> getStage(mainPane).close());
    }

}

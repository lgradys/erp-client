package warehouse.erpclient.warehouse.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import warehouse.erpclient.warehouse.dto.ItemDTO;

public class Item {

    private SimpleLongProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty quantityUnit;
    private SimpleLongProperty warehouseId;

    public Item() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();
        this.quantityUnit = new SimpleStringProperty();
        this.warehouseId = new SimpleLongProperty();
    }

    public Item(SimpleLongProperty id, SimpleStringProperty name, SimpleIntegerProperty quantity, SimpleStringProperty quantityUnit, SimpleLongProperty warehouseId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.warehouseId = warehouseId;
    }

    public static Item of(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setQuantity(itemDTO.getQuantity());
        item.setQuantityUnit(itemDTO.getQuantityUnit());
        item.setWarehouseId(itemDTO.getWarehouseId());
        return item;
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getQuantityUnit() {
        return quantityUnit.get();
    }

    public SimpleStringProperty quantityUnitProperty() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit.set(quantityUnit);
    }

    public long getWarehouseId() {
        return warehouseId.get();
    }

    public SimpleLongProperty warehouseIdProperty() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId.set(warehouseId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name=" + name +
                ", quantity=" + quantity +
                ", quantityUnit=" + quantityUnit +
                ", warehouseId=" + warehouseId +
                '}';
    }

}

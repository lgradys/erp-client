package warehouse.erpclient.warehouse.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import warehouse.erpclient.warehouse.dto.WarehouseDTO;

public class Warehouse {

    private SimpleLongProperty id;
    private SimpleStringProperty name;
    private SimpleObjectProperty<Address> address;

    public Warehouse() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleObjectProperty<>(new Address());
    }

    public Warehouse(SimpleLongProperty id, SimpleStringProperty name, SimpleObjectProperty<Address> address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static Warehouse of(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseDTO.getId());
        warehouse.setName(warehouseDTO.getName());
        warehouse.setAddress(Address.of(warehouseDTO.getAddress()));
        return warehouse;
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

    public Address getAddress() {
        return address.get();
    }

    public SimpleObjectProperty<Address> addressProperty() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    @Override
    public String toString() {
        return name.get();
    }

}

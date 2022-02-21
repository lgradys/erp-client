package warehouse.erpclient.warehouse.dto;

import warehouse.erpclient.warehouse.model.Warehouse;

public class WarehouseDTO {

    private long id;
    private String name;
    private AddressDTO address;

    public WarehouseDTO() {
    }

    public WarehouseDTO(long id, String name, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static WarehouseDTO of(Warehouse warehouse) {
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        warehouseDTO.setId(warehouse.getId());
        warehouseDTO.setName(warehouse.getName());
        warehouseDTO.setAddress(AddressDTO.of(warehouse.getAddress()));
        return warehouseDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}

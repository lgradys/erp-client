package warehouse.erpclient.warehouse.dto;

import warehouse.erpclient.warehouse.model.Item;

public class ItemDTO {

    private long id;
    private String name;
    private int quantity;
    private String quantityUnit;
    private long warehouseId;

    public ItemDTO() {
    }

    public ItemDTO(long id, String name, int quantity, String quantityUnit, long warehouseId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.warehouseId = warehouseId;
    }

    public static ItemDTO of(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setQuantityUnit(item.getQuantityUnit());
        itemDTO.setWarehouseId(item.getWarehouseId());
        return itemDTO;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }
}

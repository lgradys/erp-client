package warehouse.erpclient.warehouse.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import warehouse.erpclient.warehouse.dto.AddressDTO;

public class Address {

    private SimpleLongProperty id;
    private SimpleStringProperty street;
    private SimpleIntegerProperty streetNumber;
    private SimpleStringProperty postalCode;
    private SimpleStringProperty city;

    public Address() {
        this.id = new SimpleLongProperty();
        this.street = new SimpleStringProperty();
        this.streetNumber = new SimpleIntegerProperty();
        this.postalCode = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
    }

    public Address(SimpleLongProperty id, SimpleStringProperty street, SimpleIntegerProperty streetNumber, SimpleStringProperty postalCode, SimpleStringProperty city) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public static Address of (AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setStreet(addressDTO.getStreet());
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCity(addressDTO.getCity());
        return address;
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

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public int getStreetNumber() {
        return streetNumber.get();
    }

    public SimpleIntegerProperty streetNumberProperty() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber.set(streetNumber);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public SimpleStringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    @Override
    public String toString() {
        return streetNumber.get() + " " + street.get() + "\n" + postalCode.get() + " " + city.get();
    }

}

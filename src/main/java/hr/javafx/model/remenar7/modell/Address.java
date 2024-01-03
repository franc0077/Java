package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja entitet adrese koja je definirana nazivom ulice,
 * kućnim brojem, gradom i poštanskim brojem
 */
public class Address implements Serializable {

    public static class Builder {


        private long id;
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;

        public Builder(long id, String street) {
            this.id = id;
            this.street = street;
        }

        public Builder withHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder inCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }


        public Address build() {
            return new Address(this);
        }


    }

        private long id;
        private final String street;
        private final String houseNumber;
        private final String city;
        private final String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getHouseNumber(), address.getHouseNumber()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(getPostalCode(), address.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getHouseNumber(), getCity(), getPostalCode());
    }

    /**
     * Privatni konstruktor
     * @param builder vraća objekt
     */
    private Address(Builder builder) {
            this.id = builder.id;
            this.street = builder.street;
            this.houseNumber = builder.houseNumber;
            this.city = builder.city;
            this.postalCode = builder.postalCode;
        }

    public long getId() {
        return id;
    }
    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

}

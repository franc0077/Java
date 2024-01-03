package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Predstavlja entitet tvorice definirane imenom,
 * adresom i artiklima
 */
public class Factory extends NamedEntity implements Serializable {
    private Address address;
    private Set<Item> items;


    /**
     *
     * @param id id
     * @param name ime
     * @param address adresa
     * @param items artikli
     */
    public Factory(long id, String name, Address address, Set<Item> items) {
        super(id, name);
        this.address = address;
        this.items = items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;
        return Objects.equals(getAddress(), factory.getAddress()) && Objects.equals(getItems(), factory.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getItems());
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}

package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Predstavlja entitet za dućan definiran webAdresom i artiklima
 */
public class Store extends NamedEntity implements Serializable {
    private String webAddress;
    private Set<Item> items;


    /**
     * Inicijalizira podatak o imenu dućana, webAdresi dućana i artiklima
     * @param id id
     * @param name ime
     * @param webAddress web stranica
     * @param items artikli
     */
    public Store(long id, String name, String webAddress, Set<Item> items) {
        super(id, name);
        this.webAddress = webAddress;
        this.items = items;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return Objects.equals(getWebAddress(), store.getWebAddress()) && Objects.equals(getItems(), store.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWebAddress(), getItems());
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}

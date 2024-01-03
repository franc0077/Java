package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja apstraktnu klasu definiranu imenom
 */
public abstract class NamedEntity implements Serializable {

    private long id;
    private String name;


    /**
     * Inicijalizira podatke o imenu i id-u
     * @param id id
     * @param name ime
     */
    public NamedEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedEntity that = (NamedEntity) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

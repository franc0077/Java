package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja entitet kategorije definirane imenom i opisom
 */
public class Category extends NamedEntity implements Serializable {
    private String description;


    /**
     * Inicijalizira podatak o imenu i opisu kategorije
     * @param name ime kategorije
     * @param id id
     * @param description opis kategorije
     */
    public Category(long id, String name, String description) {
        super(id, name);
        this.description = description;
    }



    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(getDescription(), category.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

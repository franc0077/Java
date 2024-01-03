package hr.javafx.model.remenar7.modell;

import java.util.Objects;

/**
 * Predstavlja record koji je definiran iznosom popusta
 * @param iznosPopusta iznos popusta
 */
public record Discount(Integer iznosPopusta) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(iznosPopusta, discount.iznosPopusta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iznosPopusta);
    }
}

package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja entitet laptopa koji nasljeđuje klasu Item i implementira sučelje Technical
 */
public final class Laptop extends Item implements Technical, Serializable {

    private Integer trajanjeGarantnogRoka;


    /**
     * Inicijalizira podatke
     * @param id id
     * @param name ime laptopa
     * @param category kategorija
     * @param width širina laptopa
     * @param height visina laptopa
     * @param length dužina laptopa
     * @param productionCost cijena proizvodnje laptopa
     * @param sellingPrice prodajna cijena laptopa
     * @param discountAmount iznos popusta na laptop
     * @param trajanjeGarantnogRoka trajanje garancije laptopa
     */
    public Laptop(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal discountAmount, Integer trajanjeGarantnogRoka) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discountAmount);
        this.trajanjeGarantnogRoka = trajanjeGarantnogRoka;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return Objects.equals(getTrajanjeGarantnogRoka(), laptop.getTrajanjeGarantnogRoka());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTrajanjeGarantnogRoka());
    }

    public Integer getTrajanjeGarantnogRoka() {
        return trajanjeGarantnogRoka;
    }

    public void setTrajanjeGarantnogRoka(Integer trajanjeGarantnogRoka) {
        this.trajanjeGarantnogRoka = trajanjeGarantnogRoka;
    }
}

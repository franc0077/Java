package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja entitet pizze
 */

public class Pizza extends Item implements Edible, Serializable {

    public static final BigDecimal BR_KALORIJA_PO_KILOGRAMU_PIZZA = new BigDecimal(2660);

    private BigDecimal weight;


    /**
     * Definira podatke
     * @param id id
     * @param name ime pizze
     * @param category kategorija
     * @param width širina pizze
     * @param height visina pizze
     * @param length dužina pizze
     * @param productionCost cijena proizvodnje pizze
     * @param sellingPrice prodajna cijena pizze
     * @param discountAmount iznos popusta na pizzui
     * @param weight težinu pizze u g
     */
    public Pizza(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal discountAmount, BigDecimal weight) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discountAmount);
        this.weight = weight;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pizza pizza = (Pizza) o;
        return Objects.equals(getWeight(), pizza.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWeight());
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * Metoda za izračunavanje kalorija
     * @param weight težina hrane u g
     * @return broj kalorija za pizzu
     */
    @Override
    public Integer calculateKilocalories(BigDecimal weight) {
       BigDecimal tezinaUKilogramima = weight.divide(new BigDecimal(1000));
       Integer kalorije = (BR_KALORIJA_PO_KILOGRAMU_PIZZA.multiply(tezinaUKilogramima).intValue());
       return kalorije;
    }

    /**
     * Metoda za izračun cijene s obzirom na popust
     * @param sellPrice prodajna cijena
     * @param popust iznos popusta
     * @return konačna cijena pizze
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal sellPrice, BigDecimal popust) {
        BigDecimal tezinaUKilogramima = weight.divide(new BigDecimal(1000));
        BigDecimal cijena = tezinaUKilogramima.multiply(sellPrice).multiply(popust);
        return cijena;
    }
}

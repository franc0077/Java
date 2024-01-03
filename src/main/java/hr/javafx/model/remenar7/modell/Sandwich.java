package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja entitet sendviča
 */
public class Sandwich extends Item implements Edible, Serializable {

    public static final BigDecimal BR_KALORIJA_PO_KILOGRAMU_SENDVIC = new BigDecimal(2120);

    private BigDecimal weight;

    /**
     * Inicijalizira podatak o
     * @param id id
     * @param name imenu sendviča
     * @param category kategoriji
     * @param width širini sendviča
     * @param height visini sendviča
     * @param length dužini sendviča
     * @param productionCost cijeni proizvodnje sendviča
     * @param sellingPrice prodajnoj cijeni sendviča
     * @param discountAmount iznosu popusta za sendvič
     * @param weight težini sendviča u g
     */

    public Sandwich(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal discountAmount, BigDecimal weight) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discountAmount);
        this.weight = weight;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sandwich sandwich = (Sandwich) o;
        return Objects.equals(getWeight(), sandwich.getWeight());
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
     * Metoda za izračun kalorija sendviča
     * @param weight težina hrane u g
     * @return broj kalorija sendviča
     */
    @Override
    public Integer calculateKilocalories(BigDecimal weight) {
        BigDecimal tezinaUKilogramima = weight.divide(new BigDecimal(1000));
        Integer kalorije =  (BR_KALORIJA_PO_KILOGRAMU_SENDVIC.multiply(tezinaUKilogramima)).intValue();
        return kalorije;
    }

    /**
     * Metoda za izračun cijene sendviča s obzirom na popust
     * @param sellPrice prodajna cijena
     * @param popust iznos popusta
     * @return konačna cijena sendviča
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal sellPrice, BigDecimal popust) {
        BigDecimal tezinaUKilogramima = weight.divide(new BigDecimal(1000));
        BigDecimal cijena = tezinaUKilogramima.multiply(sellPrice).multiply(popust);
        return cijena;
    }
}

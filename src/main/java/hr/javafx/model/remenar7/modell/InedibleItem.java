package hr.javafx.model.remenar7.modell;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Predstavlja entitet nejestivog artikla
 */
public class InedibleItem extends Item implements Serializable {

    /**
     * Inicijalizira sljedeće podatke
     * @param id id artikla
     * @param name ime artikla
     * @param category kategorija artikla
     * @param width širina artikla
     * @param height visina artikla
     * @param length dužina artikla
     * @param productionCost cijena proizvodnje
     * @param sellingPrice prodajna cijena
     * @param discountAmount iznos popusta-
     */
    public InedibleItem(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal discountAmount) {
        super(id, name, category, width, height, length, productionCost, sellingPrice, discountAmount);
    }
}

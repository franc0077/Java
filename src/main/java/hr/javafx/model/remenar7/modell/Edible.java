package hr.javafx.model.remenar7.modell;

import java.math.BigDecimal;


public interface Edible {

     /**
      * Metoda za izračun kalorija hrane
      * @param weight težina hrane u g
      * @return broj kalorija
      */
     Integer calculateKilocalories(BigDecimal weight);

     /**
      * Metoda za izračun cijene
      * @param sellPrice prodajna cijena
      * @param popust iznos popusta
      * @return konačna cijena
      */
     BigDecimal calculatePrice(BigDecimal sellPrice, BigDecimal popust);
}

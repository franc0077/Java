package hr.javafx.model.remenar7.modell;

/**
 * Sučelje Technical kojeg može naslijediti samo Laptop
 */
public sealed interface Technical permits Laptop{

    /**
     * Metoda za povrat informacije o trajalju garancije tehničkih artikala
     * @param laptop objekt Laptop klase
     * @return trajanje garancije za laptop
     */
     static Integer dohvatiTrajanjeGarantnogRokaTehnickeRobe(Laptop laptop){
        return laptop.getTrajanjeGarantnogRoka();
    }
}

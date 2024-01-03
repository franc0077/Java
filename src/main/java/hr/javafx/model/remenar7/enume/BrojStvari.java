package hr.javafx.model.remenar7.enume;

public enum BrojStvari {

    BROJ_KATEGORIJA_ARTIKALA(3),
    BROJ_ARTIKALA(5),
    BROJ_TVORNICA(2),
    BROJ_TRGOVINA(2);


    private final Integer broj;

    BrojStvari(Integer broj) {
        this.broj = broj;
    }

    public Integer getBroj() {
        return broj;
    }
}

package hr.javafx.model.remenar7.enume;

public enum Grad {

    GRAD_ZAGREB("Zagreb", "10000"),
    GRAD_SPLIT("Split", "21000"),
    GRAD_ZADAR("Zadar", "23000"),
    GRAD_PULA("Pula", "52100");

    private final String nazivGrada;
    private final String postanskiBroj;

    Grad(String nazivGrada, String postanskiBroj) {
        this.nazivGrada = nazivGrada;
        this.postanskiBroj = postanskiBroj;
    }

    public String getNazivGrada() {
        return nazivGrada;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }
}

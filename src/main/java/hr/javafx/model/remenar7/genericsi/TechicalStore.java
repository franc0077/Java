package hr.javafx.model.remenar7.genericsi;



import hr.javafx.model.remenar7.modell.Item;
import hr.javafx.model.remenar7.modell.Store;
import hr.javafx.model.remenar7.modell.Technical;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechicalStore <T extends Technical> extends Store {

    private List<T> listaTehnickihArtikala;

    public TechicalStore(long id, String name, String webAddress, List<T> listaTehnickihArtikala) {
        super(id, name, webAddress, convertListToSet(listaTehnickihArtikala));
        this.listaTehnickihArtikala = listaTehnickihArtikala;
    }

    public List<T> getListaTehnickihArtikala() {
        return listaTehnickihArtikala;
    }

    public void setListaTehnickihArtikala(List<T> listaTehnickihArtikala) {
        this.listaTehnickihArtikala = listaTehnickihArtikala;
    }

    private static <T> Set<Item> convertListToSet(List<T> list) {
        Set<Item> set = new HashSet<>();
        for (T item : list) {
            set.add((Item) item);
        }
        return set;
    }
}

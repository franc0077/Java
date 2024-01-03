package hr.javafx.model.remenar7.genericsi;



import hr.javafx.model.remenar7.modell.Edible;
import hr.javafx.model.remenar7.modell.Item;
import hr.javafx.model.remenar7.modell.Store;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodStore <T extends Edible> extends Store {

    private List<T> listaJestivihArikala;


    public FoodStore(long id, String name, String webAddress, List<T> listaJestivihArikala) {
        super(id, name, webAddress, convertListToSet(listaJestivihArikala));
        this.listaJestivihArikala = listaJestivihArikala;
    }

    private static <T> Set<Item> convertListToSet(List<T> list) {
        Set<Item> set = new HashSet<>();
        for (T item : list) {
            set.add((Item) item);
        }
        return set;
    }

    public List<T> getListaJestivihArikala() {
        return listaJestivihArikala;
    }

    public void setListaJestivihArikala(List<T> listaJestivihArikala) {
        this.listaJestivihArikala = listaJestivihArikala;
    }
}

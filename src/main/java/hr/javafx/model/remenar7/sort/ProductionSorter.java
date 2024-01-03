package hr.javafx.model.remenar7.sort;



import hr.javafx.model.remenar7.modell.Item;

import java.util.Comparator;

public class ProductionSorter implements Comparator<Item>{

    private boolean uzlazno;

    public ProductionSorter(boolean uzlazno) {
        this.uzlazno = uzlazno;
    }

    @Override
    public int compare(Item i1, Item i2) {

        if(uzlazno){
            return i1.getSellingPrice().compareTo(i2.getSellingPrice());
        }else{
            return i2.getSellingPrice().compareTo(i1.getSellingPrice());
        }

    }
}

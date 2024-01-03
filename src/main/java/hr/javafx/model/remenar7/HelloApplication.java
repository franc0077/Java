package hr.javafx.model.remenar7;

import hr.javafx.model.remenar7.enume.Grad;
import hr.javafx.model.remenar7.modell.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class HelloApplication extends Application {

    public static final Discount popust = new Discount(15);
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("glavniEkran.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 600);
        stage.setTitle("Production application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getMainStage(){
        return mainStage;
    }


    public static Long getIdForItems(List<Item> listaArtikala){

        Long maxId = listaArtikala.stream()
                .map(a -> a.getId())
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return maxId + 1;

    }

    public static Long getIdForCategories(List<Category> listaKategorija){

        Long maxId = listaKategorija.stream()
                .map(a -> a.getId())
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return maxId + 1;

    }

    public static Long getIdForAddresses(List<Address> listaAdresa){

        Long maxId = listaAdresa.stream()
                .map(a -> a.getId())
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return maxId + 1;

    }

    public static Long getIdForFactories(List<Factory> listaTvornica){

        Long maxId = listaTvornica.stream()
                .map(a -> a.getId())
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return maxId + 1;

    }

    public static Long getIdForStores(List<Store> listaTrgovina){

        Long maxId = listaTrgovina.stream()
                .map(t -> t.getId())
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return maxId + 1;

    }

    public static boolean isStringConvertibleToBigDecimal(String value) {
        try {
            new BigDecimal(value);
            return true; // String se može pretvoriti u BigDecimal
        } catch (NumberFormatException ex) {
            return false; // String se ne može pretvoriti u BigDecimal
        }
    }

    public static boolean isStringConvertibleToInteger(String value) {
        try {
            Integer.parseInt(value);
            return true; // String se može pretvoriti u Integer
        } catch (NumberFormatException ex) {
            return false; // String se ne može pretvoriti u Integer
        }
    }

    public static int getPositionForCity(String cityName) {
        for (int i = 0; i < Grad.values().length; i++) {
            if (Grad.values()[i].getNazivGrada().equals(cityName)) {
                return i + 1; // Vraćaš poziciju, ali od 1 nadalje
            }
        }
        return -1;
    }
}
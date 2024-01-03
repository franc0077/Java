package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {

    public void showItemSearchScreen() throws IOException {

        prikaziEkran("itemSearch.fxml", "Pretraga artikala");
    }

    public void showCategorySearchScreen() throws IOException {

        prikaziEkran("categorySearch.fxml", "Pretraga kategorija");
    }

    public void showFactorySearchScreen() throws IOException {

        prikaziEkran("factorySearch.fxml", "Pretraga tvornica");
    }

    public void showStoreSearchScreen() throws IOException {

        prikaziEkran("storeSearch.fxml", "Pretraga trgovina");
    }

    public void showAddNewItemScreen() throws IOException {

        prikaziEkran("newItemScreen.fxml", "Unos novog artikla");
    }


    public void showAddNewCategoryScreen() throws IOException {

        prikaziEkran("newCategoryScreen.fxml", "Unos nove kategorije");
    }

    public void showAddNewFactoryScreen() throws IOException {

        prikaziEkran("newFactoryScreen.fxml", "Unos nove tvornice");
    }

    public void showAddNewStoreScreen() throws IOException {

        prikaziEkran("newStoreScreen.fxml", "Unos nove trgovine");
    }


    public static void prikaziEkran(String fxmlFileName, String screenTitle) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load(), 650, 600);
        HelloApplication.getMainStage().setTitle(screenTitle);
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }
}

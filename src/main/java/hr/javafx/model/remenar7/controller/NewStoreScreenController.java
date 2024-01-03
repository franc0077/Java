package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.Category;
import hr.javafx.model.remenar7.modell.Item;
import hr.javafx.model.remenar7.modell.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static hr.javafx.model.remenar7.HelloApplication.getIdForStores;
import static hr.javafx.model.remenar7.HelloApplication.popust;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.*;

public class NewStoreScreenController {

    @FXML
    private TextField nazivTrgovineTextField;
    @FXML
    private TextField webAdresaTrgovineTextField;


    private List<Store> listaTrgovina;
    private List<Item> listaArtikala;
    private List<Category> listaKategorija;

    public void initialize(){

        listaKategorija = getCategoriesFromFile();
        listaArtikala = getItemsFromFile(listaKategorija, popust);
        listaTrgovina = getStoresFromFile(listaArtikala);


    }

    public void spremiTrgovinu(){

        String nazivTrgovine = nazivTrgovineTextField.getText();
        String webAdresaTrgovine = webAdresaTrgovineTextField.getText();

        if(nazivTrgovine.isBlank() || webAdresaTrgovine.isBlank()){

            StringBuilder poruka = new StringBuilder();

            if(nazivTrgovine.isBlank()){
                poruka.append("\nNaziv Trgovine je obavezan podatak!");
            }
            if(webAdresaTrgovine.isBlank()){
                poruka.append("\nWeb adresa trgovine je obavezan podatak!");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreška kod unosa podataka");
            alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
            alert.setContentText(poruka.toString());

            alert.showAndWait();


        }else{

            Long idStore = getIdForStores(listaTrgovina);

            Store novaTrgovina = new Store(idStore, nazivTrgovine, webAdresaTrgovine, listaArtikala.stream().collect(Collectors.toSet()));

            listaTrgovina.add(novaTrgovina);
            saveStoresToFile(listaTrgovina);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje trgovina");
            alert.setHeaderText("Uspješno dodana nova Trgovina");
            alert.setContentText("Trgovina " + nazivTrgovine + " je uspješno spremljena");

            alert.showAndWait();

            try {
                IzbornikController.prikaziEkran("storeSearch.fxml", "Pretraga trgovina");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    }

}

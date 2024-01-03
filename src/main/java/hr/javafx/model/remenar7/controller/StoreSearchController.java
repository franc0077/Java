package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import static hr.javafx.model.remenar7.HelloApplication.popust;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.*;

public class StoreSearchController {

    @FXML
    private TextField nazivTrgovineTextField;
    @FXML
    private ComboBox<String> pretragaPoArtikluComboBox;
    @FXML
    private TableView<Store> trgovinaTableView;
    @FXML
    private TableColumn<Store, String> nazivTrgovineTableColumn;
    @FXML
    private TableColumn<Store, String> webAdresaTrgovineTableColumn;


    private List<Store> listaTrgovina;
    private List<Item> listaArtikala;
    private List<Category> listaKategorija;
    private List<String > listaImenaArtikala;


    public void initialize(){

        listaKategorija = getCategoriesFromFile();
        listaArtikala = getItemsFromFile(listaKategorija, popust);
        listaTrgovina = getStoresFromFile(listaArtikala);

        nazivTrgovineTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        webAdresaTrgovineTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getWebAddress()));

        trgovinaTableView.setItems(FXCollections.observableList(listaTrgovina));

        ///

        listaImenaArtikala = listaArtikala.stream()
                .map(NamedEntity::getName)
                .toList();

        pretragaPoArtikluComboBox.setItems(FXCollections.observableList(listaImenaArtikala));


    }

    public void dohvatiTrgovine(){

        String nazivTrgovine = nazivTrgovineTextField.getText();
        String odabraniArtikl = pretragaPoArtikluComboBox.getValue();

        if(!nazivTrgovine.isBlank()) {
            List<Store> filtriraneTrgovinePoNazivu = listaTrgovina.stream()
                    .filter(t -> t.getName().contains(nazivTrgovine))
                    .toList();

            trgovinaTableView.setItems(FXCollections.observableList(filtriraneTrgovinePoNazivu));

        }else if(!(odabraniArtikl == null)){
            List<Store> filtriraneTrgovinePoArtiklu = new ArrayList<>();

            for(Store trgovina : listaTrgovina){
                for(Item artikl : trgovina.getItems()){
                    if(artikl.getName().equals(odabraniArtikl)){
                        filtriraneTrgovinePoArtiklu.add(trgovina);
                    }
                }
            }


            trgovinaTableView.setItems(FXCollections.observableList(filtriraneTrgovinePoArtiklu));
        }
        else{
            trgovinaTableView.setItems(FXCollections.observableList(listaTrgovina));
        }

    }
}

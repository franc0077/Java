package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.Category;
import hr.javafx.model.remenar7.modell.Discount;
import hr.javafx.model.remenar7.modell.Item;
import hr.javafx.model.remenar7.modell.NamedEntity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.List;

import static hr.javafx.model.remenar7.HelloApplication.popust;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.getCategoriesFromFile;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.getItemsFromFile;

public class ItemSearchController {

    @FXML
    private TextField nazivArtiklaTextField;
    @FXML
    private TableView<Item> artiklTableView;
    @FXML
    private TableColumn<Item, String> nazivArtiklaTableColumn;
    @FXML
    private TableColumn<Item, String> kategorijaArtiklaTableColumn;
    @FXML
    private TableColumn<Item, BigDecimal> sirinaArtiklaTableColumn;
    @FXML
    private TableColumn<Item, BigDecimal> visinaArtiklaTableColumn;
    @FXML
    private TableColumn<Item, BigDecimal> duzinaArtiklaTableColumn;
    @FXML
    private TableColumn<Item, BigDecimal> cijenaProizvodnjeArtiklaTableColumn;
    @FXML
    private TableColumn<Item, BigDecimal> prodajnaCijenaArtiklaTableColumn;
    @FXML
    private ComboBox<String> pretragaPoKategorijiComboBox;


    private List<Item> listaArtikala;
    private List<Category> listaKategorija;
    private List<String> listaImenaKategorija;


    public void initialize(){

        listaKategorija = getCategoriesFromFile();
        listaArtikala = getItemsFromFile(listaKategorija, popust);

        nazivArtiklaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        kategorijaArtiklaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getCategory().getName()));

        sirinaArtiklaTableColumn
                .setCellValueFactory(cellData->
                        new SimpleObjectProperty<>(cellData.getValue().getWidth()));

        visinaArtiklaTableColumn
                .setCellValueFactory(cellData->
                        new SimpleObjectProperty<>(cellData.getValue().getHeight()));

        duzinaArtiklaTableColumn
                .setCellValueFactory(cellData->
                        new SimpleObjectProperty<>(cellData.getValue().getLength()));

        cijenaProizvodnjeArtiklaTableColumn
                .setCellValueFactory(cellData->
                        new SimpleObjectProperty<>(cellData.getValue().getProductionCost()));

        prodajnaCijenaArtiklaTableColumn
                .setCellValueFactory(cellData->
                        new SimpleObjectProperty<>(cellData.getValue().getSellingPrice()));

        artiklTableView.setItems(FXCollections.observableList(listaArtikala));

        ///
        listaImenaKategorija = listaKategorija.stream()
                .map(NamedEntity::getName)
                .toList();

        pretragaPoKategorijiComboBox.setItems(FXCollections.observableList(listaImenaKategorija));




    }
    public void dohvatiArtikle(){

        String nazivArtikla = nazivArtiklaTextField.getText();
        String odabranaKategorija = pretragaPoKategorijiComboBox.getValue();

        if(!nazivArtikla.isBlank()) {
            List<Item> filtriraniItemiPoNazivu = listaArtikala.stream()
                    .filter(a -> a.getName().contains(nazivArtikla))
                    .toList();

            artiklTableView.setItems(FXCollections.observableList(filtriraniItemiPoNazivu));

        }else if(!(odabranaKategorija == null)){
            List<Item> filtriraniItemiPoKategoriji = listaArtikala.stream()
                    .filter(a->a.getCategory().getName().equals(odabranaKategorija))
                    .toList();

            artiklTableView.setItems(FXCollections.observableList(filtriraniItemiPoKategoriji));
        }else{
            artiklTableView.setItems(FXCollections.observableList(listaArtikala));
        }

    }

}
package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hr.javafx.model.remenar7.HelloApplication.popust;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.*;

public class FactorySearchController {


    @FXML
    private TextField nazivTvorniceTextField;
    @FXML
    private TextField nazivUliceTextField;
    @FXML
    private ComboBox<String> pretragaPoGraduComboBox;
    @FXML
    private TableView<Factory> tvornicaTableView;
    @FXML
    private TableColumn<Factory, String> nazivTvorniceTableColumn;
    @FXML
    private TableColumn<Factory, String> nazivUliceTableColumn;
    @FXML
    private TableColumn<Factory, String> kucniBrojTableColumn;
    @FXML
    private TableColumn<Factory, String> gradTableColumn;
    @FXML
    private TableColumn<Factory, String> postanskiBrojTableColumn;

    private List<Factory> listaTvornica;
    private List<Address> listaAdresa;
    private List<Item> listaArtikala;
    private List<Category> listaKategorija;
    private Set<String> listaGradova;

    public void initialize(){

        listaAdresa = getAddressesFromFile();
        listaKategorija = getCategoriesFromFile();
        listaArtikala = getItemsFromFile(listaKategorija, popust);
        listaTvornica = getFactoriesFromFile(listaAdresa, listaArtikala);

        nazivTvorniceTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        nazivUliceTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getAddress().getStreet()));

        kucniBrojTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getAddress().getHouseNumber()));

        gradTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getAddress().getCity()));

        postanskiBrojTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));


        tvornicaTableView.setItems(FXCollections.observableList(listaTvornica));

        ///

        listaGradova = listaAdresa.stream()
                .map(Address::getCity)
                .collect(Collectors.toSet());

        pretragaPoGraduComboBox.setItems(FXCollections.observableList(listaGradova.stream().toList()));

    }

    public void dohvatiTvornice(){

        String nazivTvornice = nazivTvorniceTextField.getText();
        String nazivUlice = nazivUliceTextField.getText();
        String odabranGrad = pretragaPoGraduComboBox.getValue();

        if(!nazivTvornice.isBlank()) {
            List<Factory> filtriraneTvornicePoNazivu = listaTvornica.stream()
                    .filter(t -> t.getName().contains(nazivTvornice))
                    .toList();

            tvornicaTableView.setItems(FXCollections.observableList(filtriraneTvornicePoNazivu));

        }else if(!nazivUlice.isBlank()){
            List<Factory> filtriraneTvornicePoNazivuUlice = listaTvornica.stream()
                    .filter(t->t.getAddress().getStreet().contains(nazivUlice))
                    .toList();

            tvornicaTableView.setItems(FXCollections.observableList(filtriraneTvornicePoNazivuUlice));
        }else if(!(odabranGrad == null)){
            List<Factory> filtriranetvornicePoGradu = listaTvornica.stream()
                    .filter(t->t.getAddress().getCity().equals(odabranGrad))
                    .toList();

            tvornicaTableView.setItems(FXCollections.observableList(filtriranetvornicePoGradu));
        }else{
            tvornicaTableView.setItems(FXCollections.observableList(listaTvornica));
        }

    }

}

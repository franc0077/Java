package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.Category;
import hr.javafx.model.remenar7.modell.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

import static hr.javafx.model.remenar7.fileUtils.FileUtils.getCategoriesFromFile;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.getItemsFromFile;

public class CategorySearchController {

    @FXML
    private TextField nazivKategorijeTextField;
    @FXML
    private TextField opisKategorijeTextField;
    @FXML
    private TableView<Category> kategorijaTableView;
    @FXML
    private TableColumn<Category, String> nazivKategorijeTableColumn;
    @FXML
    private TableColumn<Category, String> opisKategorijeTableColumn;

    private List<Category> listaKategorija;

    public void initialize(){

        listaKategorija = getCategoriesFromFile();

        nazivKategorijeTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        opisKategorijeTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDescription()));

        kategorijaTableView.setItems(FXCollections.observableList(listaKategorija));

    }

    public void dohvatiKategorije(){

        String nazivKategorije = nazivKategorijeTextField.getText();
        String opisKategorije = opisKategorijeTextField.getText();

        if(!nazivKategorije.isBlank()) {
            List<Category> filtriraneKategorijePoNazivu = listaKategorija.stream()
                    .filter(k -> k.getName().contains(nazivKategorije))
                    .toList();

            kategorijaTableView.setItems(FXCollections.observableList(filtriraneKategorijePoNazivu));

        }else if(!opisKategorije.isBlank()){
            List<Category> filtriraneKategorijePoOpisu = listaKategorija.stream()
                    .filter(k->k.getDescription().contains(opisKategorije))
                    .toList();

            kategorijaTableView.setItems(FXCollections.observableList(filtriraneKategorijePoOpisu));
        }
        else{
            kategorijaTableView.setItems(FXCollections.observableList(listaKategorija));
        }

    }

}

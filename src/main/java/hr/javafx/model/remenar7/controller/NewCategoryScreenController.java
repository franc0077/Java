package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import static hr.javafx.model.remenar7.HelloApplication.getIdForCategories;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.getCategoriesFromFile;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.saveCategoryToFile;

public class NewCategoryScreenController {

    @FXML
    private TextField nazivKategorijeTextField;
    @FXML
    private TextField opisKategorijeTextField;

    private List<Category> listaKategorija;


    public void initialize(){

        listaKategorija = getCategoriesFromFile();


    }

    public void spremiKategoriju(){

        String nazivKategorije = nazivKategorijeTextField.getText();
        String  opisKategorije = opisKategorijeTextField.getText();

        Long id = getIdForCategories(listaKategorija);

        if(!nazivKategorije.isBlank() && !opisKategorije.isBlank()){
            Category novaKategorija = new Category(id, nazivKategorije, opisKategorije);
            listaKategorija.add(novaKategorija);
            saveCategoryToFile(listaKategorija);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Spremanje kategorije");
            alert.setHeaderText("Uspješno dodana nova kategorija!");
            alert.setContentText("Kategorija " + nazivKategorije + " je uspješno spremljena!");

            alert.showAndWait();

            try {
                IzbornikController.prikaziEkran("categorySearch.fxml", "Pretraga kategorija");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }else{

            StringBuilder poruka = new StringBuilder();

            if(nazivKategorije.isBlank()){
                poruka.append("\nNaziv kategorije je obavezan podatak");
            }
            if(opisKategorije.isBlank()){
                poruka.append("\nOpis kategorije je obavezan podatak");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreška kod unosa podataka");
            alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
            alert.setContentText(poruka.toString());

            alert.showAndWait();

        }


    }

}

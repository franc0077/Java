package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.enume.Grad;
import hr.javafx.model.remenar7.modell.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.*;

import static hr.javafx.model.remenar7.HelloApplication.*;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.*;

public class NewFactoryScreenController {

    @FXML
    private TextField nazivTvorniceTextField;
    @FXML
    private TextField nazivUliceTextField;
    @FXML
    private TextField kucniBrojTextField;
    @FXML
    private ComboBox<String> gradComboBox;
    @FXML
    private ComboBox<String> tipTvorniceComboBox;


    private List<Factory> listaTvornica;
    private List<Address> listaAdresa;
    private List<Item> listaArtikala;
    private List<Category> listaKategorija;

    public void initialize(){
        listaAdresa = getAddressesFromFile();
        listaKategorija = getCategoriesFromFile();
        listaArtikala = getItemsFromFile(listaKategorija, popust);
        listaTvornica = getFactoriesFromFile(listaAdresa, listaArtikala);




        gradComboBox.getItems().clear();
        gradComboBox.getItems().addAll("Zagreb", "Split", "Zadar", "Pula");

        tipTvorniceComboBox.getItems().clear();
        tipTvorniceComboBox.getItems().addAll("Food Factory", "Technical Factory");

    }

    public void spremiTvornicu(){

        String nazivTvornice = nazivTvorniceTextField.getText();
        String nazivUlice = nazivUliceTextField.getText();
        String kucniBroj = kucniBrojTextField.getText();
        String grad = gradComboBox.getValue();
        String tip = tipTvorniceComboBox.getValue();

        if(nazivTvornice.isBlank() || nazivUlice.isBlank() || kucniBroj.isBlank() || grad==null || grad.isEmpty() || tip == null || tip.isEmpty()){
            StringBuilder poruka = new StringBuilder();

            if(nazivTvornice.isBlank()){
                poruka.append("\nNaziv tvornice je obavezan podatak!");
            }
            if(nazivUlice.isBlank()){
                poruka.append("\nNaziv ulice je obavezan podatak!");
            }
            if(kucniBroj.isBlank()){
                poruka.append("\nKućni broj je obavezan podatak!");
            }
            else{
                if(!isStringConvertibleToInteger(kucniBroj)){
                    poruka.append("\nKućni broj mora biti cijeli broj!");
                }
            }
            if(grad==null || grad.isEmpty()){
                poruka.append("\nOdaberite grad u kojem se tvornica nalazi!");
            }
            if(tip == null || tip.isEmpty()){
                poruka.append("\nOdaberite tip tvornice!");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreška kod unosa podataka");
            alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
            alert.setContentText(poruka.toString());

            alert.showAndWait();

        }else{

            if(!isStringConvertibleToInteger(kucniBroj)){

                StringBuilder poruka2 = new StringBuilder();
                poruka2.append("\nKućni broj mora biti cijeli broj!");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogreška kod unosa podataka");
                alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
                alert.setContentText(poruka2.toString());

                alert.showAndWait();

            }else{

                Long idAdresa = getIdForAddresses(listaAdresa);
                String postanskiBroj = "0000";

                for(Grad g : Grad.values()){
                    if(g.getNazivGrada().equals(grad)){
                        postanskiBroj = g.getPostanskiBroj();
                    }
                }

                Address novaAdresa = new Address.Builder(idAdresa, nazivUlice)
                        .withHouseNumber(kucniBroj)
                        .inCity(grad)
                        .withPostalCode(postanskiBroj)
                        .build();

                listaAdresa.add(novaAdresa);
                saveAddressesToFile(listaAdresa);

                Long idTvornica = getIdForFactories(listaTvornica);

                if(tip.equals("Food Factory")){
                    Set<Item> listaHrane = new HashSet<>();
                    for(Item artikl : listaArtikala){
                        if(artikl.getCategory().getName().equals("Food")){
                            listaHrane.add(artikl);
                        }
                    }

                    Factory novaTvornica = new Factory(idTvornica, nazivTvornice, novaAdresa, listaHrane);
                    listaTvornica.add(novaTvornica);
                    saveFactoriesToFile(listaTvornica, listaAdresa);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Spremanje tvornice");
                    alert.setHeaderText("Uspješno dodana nova tvornica");
                    alert.setContentText("Tvornica " + nazivTvornice + " je uspješno spremljena!");

                    alert.showAndWait();

                    try {
                        IzbornikController.prikaziEkran("factorySearch.fxml", "Pretraga tvornica");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }else if(tip.equals("Technical Factory")){

                    Set<Item> listaLaptopa = new HashSet<>();
                    for(Item artikl : listaArtikala){
                        if(artikl.getCategory().getName().equals("Technical equipment")){
                            listaLaptopa.add(artikl);
                        }
                    }

                    Factory novaTvornica = new Factory(idTvornica, nazivTvornice, novaAdresa, listaLaptopa);
                    listaTvornica.add(novaTvornica);
                    saveFactoriesToFile(listaTvornica, listaAdresa);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Spremanje tvornice");
                    alert.setHeaderText("Uspješno dodana nova tvornica!");
                    alert.setContentText("Tvornica " + nazivTvornice + " je uspješno spremljena!");

                    alert.showAndWait();

                    try {
                        IzbornikController.prikaziEkran("factorySearch.fxml", "Pretraga tvornica");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }






            }
        }




    }


}

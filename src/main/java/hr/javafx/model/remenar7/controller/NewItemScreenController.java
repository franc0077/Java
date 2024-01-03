package hr.javafx.model.remenar7.controller;

import hr.javafx.model.remenar7.modell.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static hr.javafx.model.remenar7.HelloApplication.*;
import static hr.javafx.model.remenar7.fileUtils.FileUtils.*;
import static hr.javafx.model.remenar7.main.Main.odaberiPopust;

public class NewItemScreenController {

    @FXML
    private GridPane gridPane;
    @FXML
    private TextField nazivArtiklaTextField;
    @FXML
    private ComboBox<String> kategorijaArtiklaComboBox;
    @FXML
    private TextField sirinaArtiklaTextField;
    @FXML
    private TextField visinaArtiklaTextField;
    @FXML
    private TextField duzinaArtiklaTextField;
    @FXML
    private TextField cijenaProizvodnjeArtiklaTextField;
    @FXML
    private TextField prodajnaCijenaArtiklaTextField;
    @FXML
    private ComboBox<String> vrstaHraneComboBox;


    private List<Category> listaKategorija;
    private List<String> listaImenaKategorija;
    private List<Item> listaArtikala;

    public void initialize(){

        vrstaHraneComboBox.setVisible(false);

        listaKategorija = getCategoriesFromFile();
        listaArtikala = getItemsFromFile(listaKategorija, popust);

        listaImenaKategorija = listaKategorija.stream()
                .map(NamedEntity::getName)
                .toList();
        List<String> prveTriKategorije = listaImenaKategorija.stream()
                .limit(3)
                .toList();

        kategorijaArtiklaComboBox.setItems(FXCollections.observableList(prveTriKategorije));

        kategorijaArtiklaComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            // Ukloni drugi ComboBox ako nije odabrana vrijednost "Hrana"
            if (newVal != null && !newVal.equals("Food")) {
                if (gridPane.getChildren().contains(vrstaHraneComboBox)) {
                    gridPane.getChildren().remove(vrstaHraneComboBox);
                }
            } else {
                vrstaHraneComboBox.setVisible(true);
                vrstaHraneComboBox.getItems().clear();
                vrstaHraneComboBox.getItems().addAll("Sendvič", "Pizza");

                // Dodaj drugi ComboBox ako je odabrana vrijednost "Hrana"
                if (!gridPane.getChildren().contains(vrstaHraneComboBox)) {
                    gridPane.add(vrstaHraneComboBox, 1, 9);
                }
            }
        });

    }

    public void spremiArtikl(){

        String nazivArtikla = nazivArtiklaTextField.getText();
        String kategorijaArtiklaString = kategorijaArtiklaComboBox.getValue();
        String sirinaArtiklaString = sirinaArtiklaTextField.getText();
        String visinaArtiklaString = visinaArtiklaTextField.getText();
        String duzinaArtiklaString = duzinaArtiklaTextField.getText();
        String cijenaProizvodnjeArtiklaString = cijenaProizvodnjeArtiklaTextField.getText();
        String prodajnaCijenaArtiklaString = prodajnaCijenaArtiklaTextField.getText();

        if(nazivArtikla.isBlank() || (kategorijaArtiklaString == null || kategorijaArtiklaString.isEmpty()) || sirinaArtiklaString.isBlank() || visinaArtiklaString.isBlank() || duzinaArtiklaString.isBlank()
                || cijenaProizvodnjeArtiklaString.isBlank() || prodajnaCijenaArtiklaString.isBlank()){


            StringBuilder poruka = new StringBuilder();

            if(nazivArtikla.isBlank()){
                poruka.append("\nNaziv artikla je obavezan podatak!");
            }
            if(kategorijaArtiklaString == null || kategorijaArtiklaString.isEmpty()){
                poruka.append("\nOdaberite kategoriju artikla!");
            }
            if(sirinaArtiklaString.isBlank()){
                poruka.append("\nŠirina artikla je obavezan podatak!");
            }else{
               if(!isStringConvertibleToBigDecimal(sirinaArtiklaString)){
                   poruka.append("\nŠirina artikla mora biti decimalan broj!");
               }
            }
            if(visinaArtiklaString.isBlank()){
                poruka.append("\nVisina artikla je obavezan podatak!");
            }else{
                if(!isStringConvertibleToBigDecimal(visinaArtiklaString)){
                    poruka.append("\nVisina artikla mora biti decimalan broj!");
                }
            }
            if(duzinaArtiklaString.isBlank()){
                poruka.append("\nDužina artikla je obavezan podatak!");
            }else{
                if(!isStringConvertibleToBigDecimal(duzinaArtiklaString)){
                    poruka.append("\nDužina artikla mora biti decimalan broj!");
                }
            }
            if(cijenaProizvodnjeArtiklaString.isBlank()){
                poruka.append("\nCijena proizvodnje artikla je obavezan podatak!");
            }else{
                if(!isStringConvertibleToBigDecimal(cijenaProizvodnjeArtiklaString)){
                    poruka.append("\nCijena proizvodnje artikla mora biti decimalan broj!");
                }
            }
            if(prodajnaCijenaArtiklaString.isBlank()){
                poruka.append("\nProdajna cijena artikla je obavezan podatak!");
            }else{
                if(!isStringConvertibleToBigDecimal(prodajnaCijenaArtiklaString)){
                    poruka.append("\nProdajna cijena artikla mora biti decimalan broj!");
                }
            }



            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreška kod unosa podataka");
            alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
            alert.setContentText(poruka.toString());

            alert.showAndWait();

        }
        else if(!nazivArtikla.isBlank() || (kategorijaArtiklaString != null && !kategorijaArtiklaString.isEmpty()) || !sirinaArtiklaString.isBlank() || !visinaArtiklaString.isBlank() || !duzinaArtiklaString.isBlank()
        || !cijenaProizvodnjeArtiklaString.isBlank() || !prodajnaCijenaArtiklaString.isBlank()) {

            StringBuilder poruka2 = new StringBuilder();
            int prolaz = 0;

            if(!isStringConvertibleToBigDecimal(sirinaArtiklaString) || !isStringConvertibleToBigDecimal(visinaArtiklaString) || !isStringConvertibleToBigDecimal(duzinaArtiklaString)
            || !isStringConvertibleToBigDecimal(cijenaProizvodnjeArtiklaString) || !isStringConvertibleToBigDecimal(prodajnaCijenaArtiklaString)){

                if(!isStringConvertibleToBigDecimal(sirinaArtiklaString)){
                    poruka2.append("\nŠirina artikla mora biti decimalan broj!");
                }
                if(!isStringConvertibleToBigDecimal(visinaArtiklaString)){
                    poruka2.append("\nVisina artikla mora biti decimalan broj!");
                }
                if(!isStringConvertibleToBigDecimal(duzinaArtiklaString)){
                    poruka2.append("\nDužina artikla mora biti decimalan broj!");
                }
                if(!isStringConvertibleToBigDecimal(cijenaProizvodnjeArtiklaString)){
                    poruka2.append("\nCijena proizvodnje artikla mora biti decimalan broj!");
                }
                if(!isStringConvertibleToBigDecimal(prodajnaCijenaArtiklaString)){
                    poruka2.append("\nProdajna cijena artikla mora biti decimalan broj!");
                }

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogreška kod unosa podataka");
                alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
                alert.setContentText(poruka2.toString());

                alert.showAndWait();

            }else {

                Category kategorijaArtikla = null;
                for (Category kat : listaKategorija) {
                    if (kat.getName().equals(kategorijaArtiklaString)) {
                        kategorijaArtikla = kat;
                    }
                }

                BigDecimal sirinaArtikla = new BigDecimal(sirinaArtiklaString);
                BigDecimal visinaArtikla = new BigDecimal(visinaArtiklaString);
                BigDecimal duzinaArtikla = new BigDecimal(duzinaArtiklaString);
                BigDecimal cijenaProizvodnjeArtikla = new BigDecimal(cijenaProizvodnjeArtiklaString);
                BigDecimal prodajnaCijenaArtikla = new BigDecimal(prodajnaCijenaArtiklaString);
                Long id = getIdForItems(listaArtikala);
                BigDecimal popustBd = odaberiPopust(popust, 2);

                if (kategorijaArtiklaString.equals("Literature")) {

                    InedibleItem nejestiviArtikl = new InedibleItem(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, popustBd);
                    listaArtikala.add(nejestiviArtikl);
                    saveItemsToFile(listaArtikala);
                    prolaz = 1;

                } else if (kategorijaArtiklaString.equals("Technical equipment")) {

                    Integer garancijaLaptopa = 24;

                    Laptop laptop = new Laptop(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, popustBd, garancijaLaptopa);
                    listaArtikala.add(laptop);
                    saveItemsToFile(listaArtikala);
                    prolaz = 1;

                } else if (kategorijaArtiklaString.equals("Food")) {

                    String vrstaHraneCombo = vrstaHraneComboBox.getValue();

                    if(vrstaHraneCombo == null || vrstaHraneCombo.isEmpty()){

                        prolaz = 0;

                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Pogreška kod unosa podataka");
                        alert.setHeaderText("Molimo ispravite sljedeće pogreške: ");
                        alert.setContentText("Odaberite vrstu hrane (Sendvič ili Pizza)");

                        alert.showAndWait();

                    }else {
                        prolaz = 1;

                        if (vrstaHraneCombo.equals("Sendvič")) {

                            if (id % 2 == 0) {
                                id = id + 1;
                            }

                            BigDecimal tezinaSendvica = new BigDecimal(237);
                            Sandwich sendvic = new Sandwich(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, popustBd, tezinaSendvica);
                            listaArtikala.add(sendvic);
                            saveItemsToFile(listaArtikala);
                        } else if (vrstaHraneCombo.equals("Pizza")) {

                            if (id % 2 != 0) {
                                id = id + 1;
                            }

                            BigDecimal tezinaPizze = new BigDecimal(383);
                            Pizza pizza = new Pizza(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, popustBd, tezinaPizze);
                            listaArtikala.add(pizza);
                            saveItemsToFile(listaArtikala);
                        }
                    }
                }

                if(prolaz == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Spremanje artikla");
                    alert.setHeaderText("Uspješno dodan novi artikl");
                    alert.setContentText("Artikl " + nazivArtikla + " je uspješno spremljen");

                    alert.showAndWait();

                    try {
                        IzbornikController.prikaziEkran("itemSearch.fxml", "Pretraga artikala");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }

    }

}

package hr.javafx.model.remenar7.fileUtils;



import hr.javafx.model.remenar7.enume.Grad;
import hr.javafx.model.remenar7.modell.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static hr.javafx.model.remenar7.HelloApplication.getPositionForCity;
import static hr.javafx.model.remenar7.main.Main.logger;
import static hr.javafx.model.remenar7.main.Main.odaberiPopust;


public class FileUtils {

    private static final String ITEMS_FILE_NAME = "dat/items.txt";
    private static final String CATEGORIES_FILE_NAME = "dat/categories.txt";
    private static final String FACTORIES_FILE_NAME = "dat/factories.txt";
    private static final String ADDRESSES_FILE_NAME = "dat/addresses.txt";
    private static final String STORES_FILE_NAME = "dat/stores.txt";


    public static List<Item> getItemsFromFile(List<Category> kategorije, Discount popust){
        List<Item> listaArtikala = new ArrayList<>();

        File itemsFile = new File(ITEMS_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(itemsFile))){

            Optional<String> ids = Optional.empty();

            while ((ids = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Item> newItemOptional = Optional.empty();

                long id = Long.parseLong(ids.get());
                String nazivArtikla = reader.readLine();
                Category kategorijaArtikla = kategorije.get((Integer.parseInt(reader.readLine()))-1);
                BigDecimal sirinaArtikla = new BigDecimal(reader.readLine());
                BigDecimal visinaArtikla = new BigDecimal(reader.readLine());
                BigDecimal duzinaArtikla = new BigDecimal(reader.readLine());
                BigDecimal cijenaProizvodnjeArtikla = new BigDecimal(reader.readLine());
                BigDecimal prodajnaCijenaArtikla = new BigDecimal(reader.readLine());
                int popustProcitan = Integer.parseInt(reader.readLine());
                BigDecimal odabirPopusta = odaberiPopust(popust, popustProcitan);
                if(kategorijaArtikla.getId() == 1){
                    BigDecimal tezinaNamirnice = new BigDecimal(reader.readLine());
                    //PAZITI KOD ČITANJA -> Pizza ili Sendvič
                    //if(id == 2 || id == 6){
                    if(id % 2 == 0){
                        newItemOptional = Optional.of(new Pizza(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, odabirPopusta, tezinaNamirnice));

                    }else{
                        newItemOptional = Optional.of(new Sandwich(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, odabirPopusta, tezinaNamirnice));

                    }

                }else if(kategorijaArtikla.getId() == 2){
                    int garancijaLaptopa = Integer.parseInt(reader.readLine());
                    newItemOptional = Optional.of(new Laptop(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, odabirPopusta, garancijaLaptopa));
                }

                else {
                    newItemOptional = Optional.of(new InedibleItem(id, nazivArtikla, kategorijaArtikla, sirinaArtikla, visinaArtikla, duzinaArtikla, cijenaProizvodnjeArtikla, prodajnaCijenaArtikla, odabirPopusta));

                }

                if(newItemOptional.isPresent()){
                    listaArtikala.add(newItemOptional.get());
                }
            }


        }catch (IOException ex){
            String message = "Dogodila se pogreška kod čitanja datoteke!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return listaArtikala;
    }

    public static List<Category> getCategoriesFromFile(){
        List<Category> listaKategorija = new ArrayList<>();

        File categoriesFile = new File(CATEGORIES_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(categoriesFile))){

            Optional<String> ids = Optional.empty();

            while ((ids = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Category> newCategoryOptional = Optional.empty();

                long id = Long.parseLong(ids.get());
                String nazivKategorije = reader.readLine();
                String opisKategorije = reader.readLine();

                newCategoryOptional = Optional.of(new Category(id, nazivKategorije, opisKategorije));

                if(newCategoryOptional.isPresent()){
                    listaKategorija.add(newCategoryOptional.get());
                }
            }
        }catch (IOException ex){
            String message = "Dogodila se pogreška kod čitanja datoteke!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return listaKategorija;
    }

    public static List<Address> getAddressesFromFile(){
        List<Address> listaAdresa = new ArrayList<>();

        File addressesFile = new File(ADDRESSES_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(addressesFile))){

            Optional<String> ids = Optional.empty();

            while ((ids = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Address> newAddressOptional = Optional.empty();

                long id = Long.parseLong(ids.get());
                String nazivUlice = reader.readLine();
                String kucniBroj = reader.readLine();
                int grad = Integer.parseInt(reader.readLine());
                String nazivGrada = Grad.values()[grad-1].getNazivGrada();
                String postanskiBroj = Grad.values()[grad-1].getPostanskiBroj();

                newAddressOptional = Optional.of(new Address.Builder(id, nazivUlice)
                        .withHouseNumber(kucniBroj)
                        .inCity(nazivGrada)
                        .withPostalCode(postanskiBroj)
                        .build());

                if(newAddressOptional.isPresent()){
                    listaAdresa.add(newAddressOptional.get());
                }
            }
        }catch (IOException ex){
            String message = "Dogodila se pogreška kod čitanja datoteke!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return listaAdresa;
    }


    public static List<Factory> getFactoriesFromFile(List<Address> adrese, List<Item> artikli){
        List<Factory> listaTvornica = new ArrayList<>();

        File factoriesFile = new File(FACTORIES_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(factoriesFile))){

            Optional<String> ids = Optional.empty();

            while ((ids = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Factory> newFactoryOptional = Optional.empty();

                long id = Long.parseLong(ids.get());
                String nazivTvornice = reader.readLine();
                int odabirAdrese = Integer.parseInt(reader.readLine());

                String artikliString = reader.readLine();
                String[] artikliIds = artikliString.split(",");

                Set<Item> artikliZaTvornicu = new HashSet<>();
                for(String artId : artikliIds){

                    for(Item artikl : artikli){
                        if(String.valueOf(artikl.getId()).equals(artId)){
                            artikliZaTvornicu.add(artikl);
                        }
                    }
                }

                newFactoryOptional = Optional.of(new Factory(id, nazivTvornice, adrese.get(odabirAdrese - 1), artikliZaTvornicu));

                if(newFactoryOptional.isPresent()){
                    listaTvornica.add(newFactoryOptional.get());
                }
            }
        }catch (IOException ex){
            String message = "Dogodila se pogreška kod čitanja datoteke!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return listaTvornica;
    }


    public static List<Store> getStoresFromFile(List<Item> artikli){
        List<Store> listaDucana = new ArrayList<>();

        File storesFile = new File(STORES_FILE_NAME);

        try(BufferedReader reader = new BufferedReader(new FileReader(storesFile))){

            Optional<String> ids = Optional.empty();

            while ((ids = Optional.ofNullable(reader.readLine())).isPresent()){

                Optional<Store> newStoreOptional = Optional.empty();

                long id = Long.parseLong(ids.get());
                String nazivDucana = reader.readLine();
                String nazivWebStranice = reader.readLine();

                String artikliString = reader.readLine();
                String[] artikliIds = artikliString.split(",");

                Set<Item> artikliZaDucan = new HashSet<>();
                for(String artId : artikliIds){

                    for(Item artikl : artikli){
                        if(String.valueOf(artikl.getId()).equals(artId)){
                            artikliZaDucan.add(artikl);
                        }
                    }
                }

                newStoreOptional = Optional.of(new Store(id, nazivDucana, nazivWebStranice, artikliZaDucan));

                if(newStoreOptional.isPresent()){
                    listaDucana.add(newStoreOptional.get());
                }
            }
        }catch (IOException ex){
            String message = "Dogodila se pogreška kod čitanja datoteke!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return listaDucana;
    }

    public static void saveItemsToFile(List<Item> artikli){

        File itemsFile = new File(ITEMS_FILE_NAME);

        try(PrintWriter pw = new PrintWriter(itemsFile)){

            int cat = 0;

            for(Item artikl : artikli){

                if(artikl.getCategory().getName().equals("Food")){
                     cat = 1;
                }else if(artikl.getCategory().getName().equals("Technical equipment")){
                     cat = 2;
                }else if(artikl.getCategory().getName().equals("Literature")){
                     cat = 3;
                }

                pw.println(artikl.getId());
                pw.println(artikl.getName());
                pw.println(cat);
                pw.println(artikl.getWidth());
                pw.println(artikl.getHeight());
                pw.println(artikl.getLength());
                pw.println(artikl.getProductionCost());
                pw.println(artikl.getSellingPrice());

                if(artikl.getCategory().getName().equals("Food")){
                    pw.println(2);
                    if(artikl instanceof Sandwich){
                        Sandwich sendvic = (Sandwich) artikl;
                        pw.println(sendvic.getWeight());
                    }if(artikl instanceof Pizza){
                        Pizza pizza = (Pizza) artikl;
                        pw.println(pizza.getWeight());
                    }
                }else if(artikl.getCategory().getName().equals("Technical equipment")){
                    pw.println(1);
                    if(artikl instanceof Laptop){
                        Laptop laptop = (Laptop) artikl;
                        pw.println(laptop.getTrajanjeGarantnogRoka());
                    }

                }else if(artikl.getCategory().getName().equals("Literature")){
                    pw.println(2);
                }

            }

        }catch (IOException ex){
            String message = "Dogodila se pogreška kod spremanja novog artikla u datoteku!";
            logger.error(message, ex);
            System.out.println(message);
        }

    }


    public static void saveCategoryToFile(List<Category> kategorije){

        File categoriesFile = new File(CATEGORIES_FILE_NAME);

        try(PrintWriter pw = new PrintWriter(categoriesFile)){


            for(Category kategorija : kategorije){

                pw.println(kategorija.getId());
                pw.println(kategorija.getName());
                pw.println(kategorija.getDescription());

            }

        }catch (IOException ex){
            String message = "Dogodila se pogreška kod spremanja nove kategorije u datoteku!";
            logger.error(message, ex);
            System.out.println(message);
        }

    }

    public static void saveAddressesToFile(List<Address> adrese){

        File addressesFile = new File(ADDRESSES_FILE_NAME);

        try(PrintWriter pw = new PrintWriter(addressesFile)){


            for(Address adresa : adrese){

                pw.println(adresa.getId());
                pw.println(adresa.getStreet());
                pw.println(adresa.getHouseNumber());

                int pozicija = getPositionForCity(adresa.getCity());
                pw.println(pozicija);

            }

        }catch (IOException ex){
            String message = "Dogodila se pogreška kod spremanja nove adrese u datoteku!";
            logger.error(message, ex);
            System.out.println(message);
        }

    }

    public static void saveFactoriesToFile(List<Factory> tvornice, List<Address> listaAdresa){

        File factoriesFile = new File(FACTORIES_FILE_NAME);

        try(PrintWriter pw = new PrintWriter(factoriesFile)){


            for(Factory tvornica : tvornice){

              pw.println(tvornica.getId());
              pw.println(tvornica.getName());

              Long idAdr = -1L;
              for(Address a : listaAdresa){
                  if(a.getStreet().equals(tvornica.getAddress().getStreet())){
                       idAdr = a.getId();
                  }
              }

              pw.println(idAdr);

              StringBuilder stringArtikli = new StringBuilder();

                List<Item> items = tvornica.getItems().stream().toList();
                for (int i = 0; i < items.size(); i++) {
                    stringArtikli.append(items.get(i).getId());
                    if (i != items.size() - 1) {
                        stringArtikli.append(",");
                    }
                }

                pw.println(stringArtikli);

            }

        }catch (IOException ex){
            String message = "Dogodila se pogreška kod spremanja nove tvornice u datoteku!";
            logger.error(message, ex);
            System.out.println(message);
        }

    }

    public static void saveStoresToFile(List<Store> trgovine){

        File storesFile = new File(STORES_FILE_NAME);

        try(PrintWriter pw = new PrintWriter(storesFile)){


            for(Store trg : trgovine){

                pw.println(trg.getId());
                pw.println(trg.getName());
                pw.println(trg.getWebAddress());

                StringBuilder artikliString = new StringBuilder();

                List<Item> items = trg.getItems().stream().toList();
                for (int i = 0; i < items.size(); i++) {
                    artikliString.append(items.get(i).getId());
                    if (i != items.size() - 1) {
                        artikliString.append(",");
                    }
                }

                pw.println(artikliString);

            }

        }catch (IOException ex){
            String message = "Dogodila se pogreška kod spremanja nove trgovine u datoteku!";
            logger.error(message, ex);
            System.out.println(message);
        }

    }


}

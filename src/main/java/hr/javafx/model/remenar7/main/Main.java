package hr.javafx.model.remenar7.main;


import hr.javafx.model.remenar7.genericsi.FoodStore;
import hr.javafx.model.remenar7.genericsi.TechicalStore;
import hr.javafx.model.remenar7.modell.*;
import hr.javafx.model.remenar7.sort.ProductionSorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static hr.javafx.model.remenar7.fileUtils.FileUtils.*;

/**
 * Main klasa u kojoj se izvršava program
 */

public class Main {


    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final String SERIALIZATION_FACTORY_FILE_NAME = "dat/tvornice.dat";
    public static final String SERIALIZATION_STORE_FILE_NAME = "dat/trgovine.dat";


    public static void main(String[] args) {

            logger.info("Program je pokrenut!");

            Scanner scanner = new Scanner(System.in);


            List<Category> kategorije = new ArrayList<>();
            kategorije = getCategoriesFromFile();


           // Discount popust = unesiIznosPopusta(scanner);
              Discount popust = new Discount(15);

            List<Item> artikli = new ArrayList<>();
            artikli = getItemsFromFile(kategorije, popust);


            List<Address> adrese = new ArrayList<>();
            adrese = getAddressesFromFile();


            List<Factory> tvornice = new ArrayList<>();
            tvornice = getFactoriesFromFile(adrese, artikli);


            List<Store> trgovine = new ArrayList<>();
            trgovine = getStoresFromFile(artikli);



            odrediTvornicuNajvecegVolumenaArtikla(tvornice);
            odrediTrgovinuSNajjeftinijimArtiklom(trgovine, popust);


            for(Item artikl : artikli){
                if(artikl instanceof Pizza){
                    Pizza pizza = (Pizza) artikl;
                    System.out.println("Namirnica " + pizza.getName() + " ima " + pizza.calculateKilocalories(pizza.getWeight()) +
                            " kalorija i košta " + pizza.calculatePrice(pizza.getSellingPrice(), pizza.getDiscountAmount()) + " EUR");
                }
                if(artikl instanceof Sandwich){
                    Sandwich sendvic = (Sandwich) artikl;
                    System.out.println("Namirnica " + sendvic.getName() + " ima " + sendvic.calculateKilocalories(sendvic.getWeight()) +
                            " kalorija i košta " + sendvic.calculatePrice(sendvic.getSellingPrice(), sendvic.getDiscountAmount()) + " EUR");
                }
            }

            odrediNamirnicuSNajvecimBrojemKalorija(artikli);
            odrediNajskupljuNamirnicu(artikli);


            odrediLaptopSNajmanjimGarantiranimRokom(artikli);


        Map<Category,List<Item>>mapaPoKategoriji = new HashMap<>();

        for(Item artikl : artikli){

            Category kategorijaArtikla = artikl.getCategory();

            if(!mapaPoKategoriji.containsKey(kategorijaArtikla)){
                mapaPoKategoriji.put(kategorijaArtikla, new ArrayList<>());
            }

            mapaPoKategoriji.get(kategorijaArtikla).add(artikl);

        }

        ispisiNajskupljiINajeftinijiArtiklIzMape(mapaPoKategoriji);


        // 4.
        TechicalStore<Technical> trgovinaSTehnickomOpremom = unesiTrgovinuSTehnickomOpremom(artikli, trgovine);
        FoodStore<Edible> trgovinaSHranom = unesiTrgovinuSHranom(artikli, trgovine);
        trgovine.add(trgovinaSTehnickomOpremom);
        trgovine.add(trgovinaSHranom);


        System.out.println("PARAMETRIZIRANE KLASE: ");
        System.out.println("Ime prve trgovine: " + trgovinaSTehnickomOpremom.getName() + ", artikli: ");
        trgovinaSTehnickomOpremom.getItems().forEach(i -> System.out.println(i.getName()));
        System.out.println("Ime druge trgovine: " + trgovinaSHranom.getName() + ", artikli: ");
        trgovinaSHranom.getItems().forEach(i -> System.out.println(i.getName()));

        // LAMBDE
        //5.

        artikli.sort(Comparator.comparing(artikl -> artikl.getWidth().multiply(artikl.getLength().multiply(artikl.getHeight()))));
        System.out.println("Artikli sortirani prema volumenu: ");
        artikli.forEach(artikl -> System.out.println(artikl.getName() + " Širina: " + artikl.getWidth() + " Dužina: " + artikl.getLength() + " Visina: " + artikl.getHeight()));
        //

        // 6.
        List<BigDecimal> listaBigDecimal = artikli.stream()
                .map(artikl -> artikl.getWidth().multiply(artikl.getLength().multiply(artikl.getHeight())))
                .toList();
        BigDecimal prosjecanVolumen = average(listaBigDecimal, RoundingMode.HALF_UP);

        Instant startL = Instant.now();

        List<BigDecimal> listaCijenaArtikalaNadprosjecnogVolumena = artikli.stream()
                .filter(artikl -> (artikl.getWidth().multiply(artikl.getLength().multiply(artikl.getHeight()))).compareTo(prosjecanVolumen) > 0)
                .map(artikl -> artikl.getSellingPrice())
                .toList();

        System.out.println("Srednja cijena svih artikala koji imaju volumen koji ima natprosječnu vrijednost je: " + average(listaCijenaArtikalaNadprosjecnogVolumena, RoundingMode.HALF_UP) + " EUR");

        Instant endL = Instant.now();
        System.out.println("Vrijeme poterbno lambdama za izvršavanje zadatka: " + Duration.between(startL, endL));

        //BEZ LAMBDI

        Instant start = Instant.now();
        BigDecimal zbrojCijena = new BigDecimal(0);
        int brojac = 0;
        BigDecimal rezultat;
        for (Item artikl : artikli){
            if(artikl.getHeight().multiply(artikl.getWidth().multiply(artikl.getLength())).compareTo(prosjecanVolumen) > 0){
                zbrojCijena = zbrojCijena.add(artikl.getSellingPrice());
                brojac ++;
            }
        }
        rezultat = zbrojCijena.divide(new BigDecimal(brojac), 2, RoundingMode.HALF_UP);
        System.out.println("Srednja cijena artikala nadprosječnog volumena: " + rezultat + " EUR");
        Instant end = Instant.now();

        System.out.println("Vrijeme izvršavanja zadatka bez lambdi: " + Duration.between(start, end));


        // 7.

        List<Integer> listaBrojaArtikalaTrgovina = trgovine.stream()
                .map(t -> t.getItems().size())
                .toList();

        BigDecimal prosjecanBrojArtikalaPoTrgovini = averageInteger(listaBrojaArtikalaTrgovina, RoundingMode.HALF_UP);
        int prosjecanBrojArtikalaPoTrgoviniInteger = prosjecanBrojArtikalaPoTrgovini.intValue();

        System.out.println("Trgovine koje imaju natprosječan broj artikala su sljedeće: ");

        trgovine.stream()
                .filter(t -> t.getItems().size() > prosjecanBrojArtikalaPoTrgoviniInteger)
                .forEach(t -> System.out.println(t.getName() + " -> Broj artikala: " + t.getItems().size()));

        //

        // 9.

        System.out.println("Artikli koji su na popustu: ");

        List<Item> artikliSPopustom = artikli.stream()
                .filter(a -> a.getDiscountAmount().compareTo(BigDecimal.ONE) != 0)
                .toList();

        Optional<List<Item>> optionalArtikliSPopustom = Optional.of(artikliSPopustom);

        optionalArtikliSPopustom.ifPresentOrElse(
                artikl -> {
                    artikl.forEach(a -> System.out.println(a.getName() + " -> Cijena: " + a.getSellingPrice().multiply(a.getDiscountAmount()) + " EUR"));
                },
                () -> System.out.println("Nema artikala na popustu.")
        );

        //


        // 10.

        trgovine.stream()
                .map(t -> "Trgovina " + t.getName() + " ima sljedeći broj artikala: " + t.getItems().size())
                .forEach(System.out::println);

        //


        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FACTORY_FILE_NAME))){

            for(Factory tvornica : tvornice){
                if(tvornica.getItems().size() >= 5) {
                    out.writeObject(tvornica);
                }
            }


        }catch (IOException ex){
            String message = "Dogodila se pogreška prilikom serijalizacije objekta tvornica!";
            logger.error(message, ex);
            System.out.println(message);
        }

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_STORE_FILE_NAME))){

            for(Store trgovina : trgovine){
                if(trgovina.getItems().size() >= 5){
                    out.writeObject(trgovina);
                }
            }

        }catch (IOException ex){
            String message = "Dogodila se pogreška prilikom serijalizacije objekta trgovina!";
            logger.error(message, ex);
            System.out.println(message);
        }


    }

    public static long dodijeliIdTrgovini(List<Store> trgovine){
        long maxId = 0;
        for(Store trgovina : trgovine){
            if(trgovina.getId() > maxId){
                maxId = trgovina.getId();
            }
        }
        return maxId + 1L;
    }

    public static <T extends Technical> TechicalStore<T> unesiTrgovinuSTehnickomOpremom(List<Item> artikli, List<Store> trgovine) {
            long id = dodijeliIdTrgovini(trgovine);
            String imeTrgovine = "iStyle";
            String nazivWebStranice = "www.istyle.hr";
            List<T> listaTehnickihStvari = artikli.stream()
                    .filter(a-> a.getCategory().getName().equals("Technical equipment"))
                    .map(a -> (T) a)
                    .toList();
            return new TechicalStore<>(id ,imeTrgovine, nazivWebStranice, listaTehnickihStvari);

    }

   public static <T extends Edible> FoodStore<T> unesiTrgovinuSHranom(List<Item> artikli, List<Store> trgovine) {
       long id = dodijeliIdTrgovini(trgovine);
        String imeTrgovine = "Burger King";
        String nazivWebStranice = "www.burgerking.hr";
        List<T> listahrane = artikli.stream()
                .filter(a-> a.getCategory().getName().equals("Food"))
                .map(a -> (T) a)
                .toList();
        return new FoodStore<>(id, imeTrgovine, nazivWebStranice, listahrane);

    }

    public static BigDecimal average(List<BigDecimal> bigDecimals, RoundingMode roundingMode) {
        BigDecimal sum = bigDecimals.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long count = bigDecimals.stream().filter(Objects::nonNull).count();
        return sum.divide(new BigDecimal(count), roundingMode);
    }

    public static BigDecimal averageInteger(List<Integer> integers, RoundingMode roundingMode) {
        if (integers == null || integers.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = new BigDecimal(integers.stream()
                .mapToInt(Integer::intValue)
                .sum());

        BigDecimal count = new BigDecimal(integers.size());
        return sum.divide(count, roundingMode);
    }

    /**
     * Funkcija za unos popusta
     * @param scanner objekt Scanner
     * @return iznos popusta
     */
   /* public static Discount unesiIznosPopusta(Scanner scanner){

        int uneseniPopust = 0;
        boolean nastaviPetlju = false;
        do {
            try {
                System.out.print("Unesite iznos popusta koji će se ponuditi za proizvode (%): ");
                uneseniPopust = scanner.nextInt();
                if(uneseniPopust ==0){
                    System.out.println("Morate unijeti popust veći od 0!");
                    nastaviPetlju = true;
                }else {
                    nastaviPetlju = false;
                }

            }catch (InputMismatchException e){
                logger.error("Unesena pogrešna vrijednost za broj (Iznos popusta)!", e);
                System.out.println("Unesite brojčanu vrijednost!");
                scanner.nextLine();
                nastaviPetlju = true;

            }
        }while(nastaviPetlju);

        return new Discount(uneseniPopust);
    } */

    /**
     * Funkcija za odabir popusta
     * @param popust popust
     * @param brojOdabira odabir
     * @return iznos popusta u decimalnom obliku (pr. 0.25 -> 25%)
     */
    public static BigDecimal odaberiPopust(Discount popust, int brojOdabira){

        BigDecimal decimalniPopust = new BigDecimal(popust.iznosPopusta()).divide(new BigDecimal(100));
        BigDecimal popustMnozenje = new BigDecimal(1).subtract(decimalniPopust);

        if(brojOdabira == 1){
            return popustMnozenje;
        }else{
            return new BigDecimal(1);
        }

    }


    /**
     * Funkcija za unos cijelog broja, ako se upiše slovo ili decimalni broj baca se iznimka
     * @param scanner objekt Scanner
     * @param brojOdabira  broj mogućih odabira
     * @return vraća se odabir
     */
    private static Integer unosCijelogBrojaOdabir(Scanner scanner, Integer brojOdabira) {
        Integer broj = null;
        boolean nastaviPetlju = true;

        while (nastaviPetlju) {
            try {
                System.out.print("Odabir >> ");
                broj = scanner.nextInt();
                scanner.nextLine();
                if (broj < 1 || broj > brojOdabira) {
                    System.out.println("Morate unijeti jedan od gore ponuđenih brojeva");
                } else {
                    nastaviPetlju = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Unesite ispravnu vrijednost (cijeli broj)!");
                logger.error("Unesena je pogrešna vrijednost umjesto cijelog broja (Odabir)!", e);
                scanner.nextLine();
            }
        }
        return broj;
    }

    /**
     * Funkcija za određivanje tvornice koja sadrži artikl najvećeg volumena
     * @param tvornice unesene tvornice
     */

    public static void odrediTvornicuNajvecegVolumenaArtikla(List<Factory> tvornice){

        BigDecimal volumen;
        BigDecimal najveciVolumen = new BigDecimal(0);
      //  Factory tvornicaSArtiklomNajvecegVolumena = null;
        Optional<Factory> tvornicaSArtiklomNajvecegVolumena = Optional.empty();


        for(Factory tvornica : tvornice){
            for(Item artikl : tvornica.getItems()){
                volumen = artikl.getHeight().multiply(artikl.getLength());
                volumen = volumen.multiply(artikl.getWidth());
                if(volumen.compareTo(najveciVolumen) > 0){
                    najveciVolumen = volumen;
                    tvornicaSArtiklomNajvecegVolumena = Optional.of(tvornica);
                }
            }
        }

        tvornicaSArtiklomNajvecegVolumena.ifPresent(factory -> System.out.println("Tvornica koja sadrži artikl najveceg volumena je:  " + factory.getName()));
    }


    /**
     * Funkcija zaodređivanje trgovine koja sadrži artikl s najnižom cijenom
     * @param trgovine unesene trgovine
     * @param popust iznos popusta
     */

    public static void odrediTrgovinuSNajjeftinijimArtiklom(List<Store> trgovine, Discount popust){

        BigDecimal minCijena = new BigDecimal(99999999);
      //  Store trgovinaSArtiklomNajnizeCjene = null;
        Optional<Store> trgovinaSArtiklomNajnizeCjeneOptional = Optional.empty();


        for(Store trgovina : trgovine){
            for(Item artikl : trgovina.getItems()){
                if(artikl.getSellingPrice().multiply(new BigDecimal(popust.iznosPopusta())).compareTo(minCijena) < 0){
                    minCijena = artikl.getSellingPrice().multiply(new BigDecimal(popust.iznosPopusta()));
                    trgovinaSArtiklomNajnizeCjeneOptional = Optional.of(trgovina);
                }
            }
        }

        if(trgovinaSArtiklomNajnizeCjeneOptional.isPresent()){
            Store trgovinaSArtiklomNajnizeCjene = trgovinaSArtiklomNajnizeCjeneOptional.get();
            System.out.println("Trgovina koja sadrži artikl s najnižom cijenom je :  " + trgovinaSArtiklomNajnizeCjene.getName());
        }


    }

    /**
     * Funkcija za određivanje namirnice koja sadrži najviše kalorija
     * @param artikli uneseni artikli
     */
    public static void odrediNamirnicuSNajvecimBrojemKalorija(List<Item> artikli) {

        Integer maxKalorijeNamirnica = 0;
       // Item artiklSNajviseKalorija = null;
        Optional<Item> artiklSNajviseKalorijaOptional = Optional.empty();

          for(Item artikl : artikli) {

              if (artikl instanceof Pizza) {
                  Pizza pizza = (Pizza) artikl;
                  if (pizza.calculateKilocalories(pizza.getWeight()).compareTo(maxKalorijeNamirnica) > 0) {
                      maxKalorijeNamirnica = pizza.calculateKilocalories(pizza.getWeight());
                      artiklSNajviseKalorijaOptional = Optional.of(artikl);
                  }
              }

              if (artikl instanceof Sandwich) {
                  Sandwich sendvic = (Sandwich) artikl;
                  if (sendvic.calculateKilocalories(sendvic.getWeight()).compareTo(maxKalorijeNamirnica) > 0) {
                      maxKalorijeNamirnica = sendvic.calculateKilocalories(sendvic.getWeight());
                      artiklSNajviseKalorijaOptional = Optional.of(artikl);
                  }
              }
          }

        if(artiklSNajviseKalorijaOptional.isPresent()){

            Item artiklSNajviseKalorija = artiklSNajviseKalorijaOptional.get();


            if (artiklSNajviseKalorija instanceof Sandwich) {
                Sandwich najviseKalorijaArtikl = (Sandwich) artiklSNajviseKalorija;
                System.out.println("Namirnica s najvise kalorija je " + najviseKalorijaArtikl.getName() + ". Sadrži " +
                        najviseKalorijaArtikl.calculateKilocalories(najviseKalorijaArtikl.getWeight()) + " kalorija!");
            }

            if (artiklSNajviseKalorija instanceof Pizza) {
                Pizza najviseKalorijaArtikl = (Pizza) artiklSNajviseKalorija;
                System.out.println("Namirnica s najvise kalorija je " + najviseKalorijaArtikl.getName() + ". Sadrži " +
                        najviseKalorijaArtikl.calculateKilocalories(najviseKalorijaArtikl.getWeight()) + " kalorija!");
            }

        }else {
            System.out.println("Trenutno ne postoji niti jedna namirnica, te se ne može odrediti namirnica s najviše kalorija!");
        }



    }

    /**
     * Funkcija za određivanje najskuplje namirnice
     * @param artikli uneseni artikli
     */

    public static void odrediNajskupljuNamirnicu(List<Item> artikli){

        BigDecimal najskupljaNamirnica = new BigDecimal(0);
      //  Item najskupljiArtiklNamirnica = null;
        Optional<Item> najskupljiArtiklNamirnicaOptional = Optional.empty();

          for(Item artikl : artikli) {

              if (artikl instanceof Pizza) {
                  Pizza pizza = (Pizza) artikl;
                  if (pizza.calculatePrice(pizza.getSellingPrice(), pizza.getDiscountAmount()).compareTo(najskupljaNamirnica) > 0) {
                      najskupljaNamirnica = pizza.calculatePrice(pizza.getSellingPrice(), pizza.getDiscountAmount());
                      najskupljiArtiklNamirnicaOptional = Optional.of(artikl);
                  }
              }

              if (artikl instanceof Sandwich) {
                  Sandwich sendvic = (Sandwich) artikl;
                  if (sendvic.calculatePrice(sendvic.getSellingPrice(), sendvic.getDiscountAmount()).compareTo(najskupljaNamirnica) > 0) {
                      najskupljaNamirnica = sendvic.calculatePrice(sendvic.getSellingPrice(), sendvic.getDiscountAmount());
                      najskupljiArtiklNamirnicaOptional = Optional.of(artikl);
                  }
              }
          }

        if(najskupljiArtiklNamirnicaOptional.isPresent()){

            Item najskupljiArtiklNamirnica = najskupljiArtiklNamirnicaOptional.get();

            if (najskupljiArtiklNamirnica instanceof Sandwich) {
                Sandwich najskuplje = (Sandwich) najskupljiArtiklNamirnica;
                System.out.println("Najskuplja namirnica je " + najskuplje.getName() + ". Košta " +
                        najskuplje.calculatePrice(najskuplje.getSellingPrice(), najskuplje.getDiscountAmount()) + " EUR!");
            }

            if (najskupljiArtiklNamirnica instanceof Pizza) {
                Pizza najskuplje = (Pizza) najskupljiArtiklNamirnica;
                System.out.println("Najskuplja namirnica je " + najskuplje.getName() + ". Košta " +
                        najskuplje.calculatePrice(najskuplje.getSellingPrice(), najskuplje.getDiscountAmount()) + " EUR!");
            }

        }else {
            System.out.println("Trenutno ne postoji niti jedna namirnica i ne može se odrediti najskuplja namirnica!");
        }


    }

    /**
     * Funkcija za određivanje laptopa sa najmanjim trajanjem garancije
     * @param artikli uneseni artikli
     */
    public static void odrediLaptopSNajmanjimGarantiranimRokom(List<Item> artikli){
        int najmanjRok = 999999;
        Optional<Laptop> laptopSMajmanjimGarantiranimRokom = Optional.empty();

          for(Item artikl : artikli) {

              if (artikl instanceof Laptop) {
                  Laptop laptop = (Laptop) artikl;
                  if (Technical.dohvatiTrajanjeGarantnogRokaTehnickeRobe(laptop) < najmanjRok) {
                      najmanjRok = Technical.dohvatiTrajanjeGarantnogRokaTehnickeRobe(laptop);
                      laptopSMajmanjimGarantiranimRokom = Optional.of(laptop);
                  }
              }
          }


        if(laptopSMajmanjimGarantiranimRokom.isPresent()){
            Laptop laptop = laptopSMajmanjimGarantiranimRokom.get();
            System.out.println("Laptop s najmanjim Garantiranim rokom je " + laptop.getName() + ". Rok iznosi " +
                    Technical.dohvatiTrajanjeGarantnogRokaTehnickeRobe(laptop) + " mjeseci!");
        }else {
            System.out.println("Trenutno nema unesenih laptopa i ne može se pronaći laptop s najmanjim garantnim rokom!");
        }

    }


    public static void ispisiNajskupljiINajeftinijiArtiklIzMape(Map<Category, List<Item>> mapaPoKategoriji){

        for(Category kategorija : mapaPoKategoriji.keySet()){
            List<Item> artikliUKategoriji = mapaPoKategoriji.get(kategorija);

            Optional.ofNullable(artikliUKategoriji)
                    .filter(list -> !list.isEmpty())
                    .ifPresentOrElse(
                            artikli -> {
                                artikli.sort(new ProductionSorter(true));
                                Item najjeftinijiArtikl = artikli.get(0);
                                System.out.println("Najjeftiniji artikl u kategoriji " + kategorija.getName() + " je: " + najjeftinijiArtikl.getName());

                                artikli.sort(new ProductionSorter(false));
                                Item najskupljiArtikl = artikli.get(0);
                                System.out.println("Najskuplji artikl u kategoriji " + kategorija.getName() + " je: " + najskupljiArtikl.getName());
                            },
                            () -> System.out.println("Nema artikala u kategoriji " + kategorija.getName())
                    );

        }

    }


}

package p1;

import p1.Cars.*;
import p1.Cars.CargoCars.*;
import p1.Exeptions.TrainValidationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Task_CLI implements Runnable {
    RailSystem railSystem;

    String operationalStationName = "";
    String operationalTrainKey = "";
    String operationalLocomotiveName = "";
    String operationalCarName = "";
    boolean operationalCarIsCargo = false;


    public Task_CLI(RailSystem railSystem) {
        this.railSystem = railSystem;
    }


    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commandlist();
        while (true) {
//            System.out.println("hi");
            try {
                if (System.in.available() > 0) {
                    char c = (char) reader.read();
                    if (c == 'r') {
                        System.out.println("Wciśnąłeś r");
                    } else if (c == 's') {
                        System.out.println("Wciśnąłeś s");
                        newStation();
                        commandlist();
                    } else if (c == 't') {
                        test();
                        System.out.println("Wciśnąłeś t");
                        newTrain();
                        commandlist();
                    } else if (c == 'l') {
                        System.out.println("Wciśnąłeś l");
                        System.out.println("Lokomowywę można stworzyć tylko na jakiejś stacji");
                        chooseStation();
                        newLocomotive();
                        commandlist();
                    } else if (c == 'c') {
                        System.out.println("Wciśnąłeś c");
                        System.out.println("Wagon można stworzyć tylko na jakiejś stacji");
                        chooseStation();
                        System.out.println("Teraz tworzymy Wagon na stacji: " + operationalStationName);
                        chooseCarType();
                        newCar();
                        System.out.println("Wagon pomyślnie utworzony");
                        commandlist();
                    } else {
                        if (c == '\n') {// tu nic nie powinno się wyswietlić
                        } else System.out.println("Niema takiego polecenia");
                    }
                } else if (railSystem.alerts.size() > 0) {
                    String alert = railSystem.alerts.remove();
                    System.out.println(alert);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Scanner sc = new Scanner(System.in);

    public void commandlist() {
        System.out.println("Lista poleceń:");
        System.out.println("r - generowanie raportu dotyczącego konkretnego pociągu");
        System.out.println("s - tworzenie nowej stacji");
        System.out.println("t - tworzenie nowego pociągu");
        System.out.println("l - tworzenie nowej lokomortywy");
        System.out.println("c - tworzenie nowego wagonu");
    }

    public void chooseStation() {
        Scanner sc = new Scanner(System.in);
        boolean stationDecisionSucces = false;

        System.out.println("Jeśli chcesz utworzyć nową stację to wpisz: ns ");
        System.out.println("Jeśli chcesz wybrac stację z listy to wpisz: ls ");

        while (!stationDecisionSucces) {
            String stationDecision = "";
            stationDecision = sc.nextLine();
            if (stationDecision.equals("ls")) {
                //tworzę tymczasową listę stacji
                List<String> listedStations = new LinkedList<>();
                for (Map.Entry<String, Station> entry : railSystem.stations.entrySet()) {
                    listedStations.add(entry.getKey());
                }
                // wyswietlam listę stacji
                for (String station : listedStations) {
                    System.out.println(station);
                }
                System.out.println("Wpisz albo skopiuj nazwę stacji");
                // walidacja nazwy stacji
                while (!listedStations.contains(stationDecision)) {
                    stationDecision = sc.nextLine();
                    if (!listedStations.contains(stationDecision)) {
                        System.out.println("Wybrana przez ciebie stacja nie istnieje");
                    }
                }
                // sukces:
                if (listedStations.contains(stationDecision)) {
                    stationDecisionSucces = true;
                    operationalStationName = stationDecision;
                }
            } else if (stationDecision.equals("ns")) {
                System.out.println("Tworzymy nową stację");
                newStation();
                stationDecisionSucces = true;
            } else {
                System.out.println("poprawny zapis to 2 małe litery ls lub ns, popraw bład");
            }

        }
    }

    public void chooseLocomotive() {
        boolean locomotiveDecisionSucces = false;

        System.out.println("Jeśli chcesz utworzyć nową lokomotywę to wpisz: nl ");
        System.out.println("Jeśli chcesz wybrac lokomotywę z listy dostępnych na tej stacji to wpisz: ll ");

        while (!locomotiveDecisionSucces) {
            String locoDecision = "";
            locoDecision = sc.nextLine();
            if (locoDecision.equals("ll")) {
                //tworzę tymczasową listę lokomotyw na stacji
                List<String> listedLocomotives = new LinkedList<>();
                listedLocomotives.clear();
                for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
                    if (entry.getValue().getHomeStation().equals(operationalStationName))
                        listedLocomotives.add(entry.getKey());

                }
                // wyswietlam listę lokomotyw
                if (listedLocomotives.size() == 0) {
                    System.out.println("Na tej stacji niema lokomotyw ");
                    chooseLocomotive();
                } else
                    System.out.println("Lista lokomotyw:");

                for (String locomotive : listedLocomotives) {
                    System.out.println(locomotive);
                }
                System.out.println("Wpisz albo skopiuj nazwę lokomotywy w której chcesz tworzyć pociąg");
                // walidacja nazwy lokomotywy
                while (!listedLocomotives.contains(locoDecision)) {
                    locoDecision = sc.nextLine();
                    if (!listedLocomotives.contains(locoDecision)) {
                        System.out.println("Wybrana przez ciebie lokomotywa nie istnieje");
                    }
                }
                // sukces:
                if (listedLocomotives.contains(locoDecision)) {
                    locomotiveDecisionSucces = true;
                    operationalLocomotiveName = locoDecision;
                    railSystem.locomotives.get(operationalLocomotiveName).setHomeStation(null);
                }
            } else if (locoDecision.equals("nl")) {
                System.out.println("Tworzymy nową lokomotywę");
                newLocomotive();
                locomotiveDecisionSucces = true;
            } else {
                System.out.println("poprawny zapis to 2 małe litery ll lub nl, popraw bład");
            }

        }
    }

    public void chooseCar() {
        boolean carDecisionSucces = false;

        System.out.println("Jeśli chcesz utworzyć nowy wagon to wpisz: nw ");
        System.out.println("Jeśli chcesz wybrac wagon z listy dostępnych na tej stacji to wpisz: lw ");

        while (!carDecisionSucces) {
            String carDecision = "";
            carDecision = sc.nextLine();
            if (carDecision.equals("lw")) {
                //tworzę tymczasową listę wagonów na stacji
                List<String> listedCars = new LinkedList<>();
                for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
                    if (entry.getValue().getHomeStation().equals(operationalStationName))
                        //problem pojawia się kiedy prubuję stworzyć pustą listę
                        listedCars.add(entry.getKey());
                }
                // wyswietlam listę wagonów
                if (listedCars.size() == 0) {
                    System.out.println("Na tej stacji niema nieprzydzielonych wagonów");
                    chooseCar();
                } else
                    System.out.println("Lista wagonów:");

                for (String car : listedCars) {
                    System.out.println(car);
                }
                System.out.println("Wpisz albo skopiuj nazwę wagonu który chcesz dodać do pociągu");
                // walidacja nazwy wagonu
                while (!listedCars.contains(carDecision)) {
                    carDecision = sc.nextLine();
                    if (!listedCars.contains(carDecision)) {
                        System.out.println("Wybrany przez ciebie wagon nie istnieje");
                    }
                }
                // sukces:
                if (listedCars.contains(carDecision)) {
                    carDecisionSucces = true;
                    operationalCarName = carDecision;
                }
            } else if (carDecision.equals("nw")) {
                System.out.println("Tworzymy nowy wagon");
                newCar();
                carDecisionSucces = true;
            } else {
                System.out.println("poprawny zapis to 2 małe litery lw lub nw, popraw bład");
            }

        }
    }


    public void newStation() {
        Scanner sc = new Scanner(System.in);
        boolean keySuccess = false;
        //tworzę tymczasową listę stacji
        List<String> listedStations = new LinkedList<>();
        for (Map.Entry<String, Station> entry : railSystem.stations.entrySet()) {
            listedStations.add(entry.getKey());
        }

        System.out.println("Nazwa stacji powinna być pisana dużymi literami \nbez spacji, liczb i innych znaków");
        System.out.println("Podaj nazwę stacji");

        while (!keySuccess) {
            String key = sc.nextLine();

            if (listedStations.contains(key)) {
                System.out.println("Taka stacja już istnieje wymyśl inną nazwę dla nowej stacji");
            } else {
                if (key.matches("[A-Z]+")) {
                    Station station = new Station(key);
                    railSystem.stations.put(key, station);
                    operationalStationName = key;
                    System.out.println("Utworzyłeś nową stację: " + operationalStationName);
                    keySuccess = true;

                } else {
                    System.out.println("Nazwa stacji powinna być pisana dużymi literami \nbez spacji, liczb i innych znaków");
                    System.out.println("Podaj nazwę stacji");
                }
            }


        }


    }


    // operacyjne zmienne dla newTrain

    String key = "";
    //    boolean cargo = false;
    String carType = "";

    //    boolean addCarsSuccess = false;
    // operacyjne zmienne dla dodawania wagonów do pociągu
    boolean carTypeSucces = false;

    public void newTrain() {
        System.out.println("Pociąg można stworzyć tylko na jakiejś stacji");
        chooseStation();

        System.out.println("Teraz tworzymy pociąg na stacji: " + operationalStationName);
        trainKeyInterface();

        System.out.println("Dodajmy lokomotywę do naszego pociągu");
        chooseLocomotive();
        railSystem.trains.get(operationalTrainKey).setlocomotiveName(operationalLocomotiveName);


        System.out.println("To teraz dodajemy wagony do lokomotywy: "
                + operationalLocomotiveName + " na stacji: " + operationalStationName);


        trainAddCarsInterface();

        System.out.println("Twój pociąg: ");
        System.out.println(railSystem.trains.get(operationalTrainKey));
        System.out.println("Wagony: ");
        railSystem.trains.get(operationalTrainKey).showCars();
        System.out.println("Pociąg utworzony poprawnie, podaj następne polecenie");


    }

    // newTrain potrzebne elementy (początek)

    public void trainAddCarsInterface() {
        boolean addCarSucces = false;

        while (!addCarSucces) {
            chooseCar();
            String prevHomeStation = railSystem.cars.get(operationalCarName).getHomeStation();

            try {
                railSystem.cars.get(operationalCarName).setHomeStation(null);
                railSystem.cars.get(operationalCarName).setTrainKey(operationalTrainKey);
                railSystem.trains.get(operationalTrainKey).carsNames.add(operationalCarName);
                validateTrain();
                addCarSucces = true;
            } catch (TrainValidationException e) {
                railSystem.cars.get(operationalCarName).setHomeStation(prevHomeStation);
                railSystem.cars.get(operationalCarName).setTrainKey(null);
                railSystem.trains.get(operationalTrainKey).carsNames.remove(operationalCarName);
                System.out.println("Błąd: " + e.getMessage());
            }
        }
        System.out.println("Chcesz dodać jeszcze jeden wagon do tego pociągu?");
        System.out.println("Wpisz t jeśli tak lub n jeśli nie");

        boolean addMoreSucces = false;
        while (!addMoreSucces) {
            String addMore = sc.nextLine();
            if (addMore.length() > 1 || addMore.length() < 1)
                System.out.println("Nie wygłupiaj się wpisz t lub n");
            else {
                if (addMore.equals("t")) {
                    addMoreSucces = true;
                    trainAddCarsInterface();
                } else if (addMore.equals("n")) {
                    addMoreSucces = true;
                } else {
                    System.out.println("Nie wygłupiaj się wpisz t lub n");
                }
            }
        }
    }

    public void trainKeyInterface() {
        boolean keySuccess = false;
        // Interfejs klucza pociągu
        System.out.println("Podaj identyfikator pociągu (klucz)");
        System.out.println();
        System.out.println("""
                Przykład:
                Cargo train: CT 0001 - (pociąg ciężarowy)
                Passenger train: PT 0001 - (pociąg osobowy)
                                
                Uwaga! Pierwsze 2 litery są bardzo ważne od nich zależy jakiego typu będzie to pociąg.
                Bagaż i pocztę można przewozić tylko pociągami osobowymi""");

        while (!keySuccess) {
            //tymczasowa lista nazw pociągów
            List<String> trainKeys = new ArrayList<>();
            for (Map.Entry<String, Train> entry : railSystem.trains.entrySet()) {
                trainKeys.add(entry.getKey());
            }

            key = sc.nextLine();
            System.out.println();

            // walidacja do pola key
            if (key.length() != 7 || !key.contains(" "))
                System.out.println("Klucz który podałeś nie jest odpowiedni, poprawny format zapisu to : PT #### lub CT ####");
            else if (!Character.isUpperCase(key.charAt(0)) && !Character.isUpperCase(key.charAt(1))) {
                System.out.println("Dwie pierwsze litery powinne być duże i w czytelny sposób reprezentować typ pociągu" +
                        " na przykład PT - Passenger Train");
            } else if (!Character.isDigit(key.charAt(3)) && !Character.isDigit(key.charAt(4))
                    && !Character.isDigit(key.charAt(5)) && !Character.isDigit(key.charAt(6))) {
                System.out.println("Cztery ostatnie znaki powinny być liczbami np: CT 0001");
            } else if (key.charAt(3) == 0 && key.charAt(4) == 0
                    && key.charAt(5) == 0 && key.charAt(6) == 0) {
                System.out.println("Numer pociągu nie może być: 0000");
            } else if ((key.charAt(0) != 'P' && key.charAt(1) != 'T') || (key.charAt(0) != 'C' && key.charAt(1) != 'T')) {
                System.out.println("Pierwsze 2 litery powinne reprezentować jeden z typów pociągu, PT lub CT");
            } else if (trainKeys.contains(key)) {
                System.out.println("Istnieje już pociąg z takim kluczem, wymyśl nowy klucz");
            } else {
// Tworzymy nowy pociąg
                railSystem.trains.put(key, new Train(key));
                if (key.charAt(0) == 'C') {
                    operationalCarIsCargo = true;
                } else if (key.charAt(0) == 'P') {
                    operationalCarIsCargo = false;
                }
                keySuccess = true;
                operationalTrainKey = key;
                System.out.println("Udało się stworzyłeś pociąg");
            }
        }
    }


//    public void trainAddCarsInterface() {
//        // Interfejs dodawania wagonów
//        if (keySuccess) {
//            System.out.println("Wybierz numer wagonu który chcesz dodać do naszego pociągu:");
//            if (!operationalCarIsCargo) {
//                System.out.println("""
//                        1.Wagon bagażowo-pocztowy
//                        2.Wagon pasażerski
//                        3.Wagon pocztowy
//                        4.Wagon restauracyjny
//                        """);
//            } else if (operationalCarIsCargo) {
//                System.out.println("""
//                        1.Wagon towarowy podstawowy
//                        2.Wagon na materiały wybuchowe
//                        3.Wagon na materiały gazowe
//                        4.Wagon towarowy ciężki
//                        5.Wagon materiały ciekłe
//                        6.Wagon na ciekłe materiały toksyczne
//                        7.Wagon chłodniczy
//                        8.Wagon na materiały toksyczne
//                        """);
//            }
//            while (!addCarsSuccess) {
//                while (!carTypeSucces) {
//                    carType = sc.nextLine();
//                    if (carType.length() != 1) {
//                        System.out.println("Bład! Wpisz cyfrę odpowiednią do typu wagonu który chcesz dodać");
//                    } else if (!Character.isDigit(carType.charAt(0))) {
//                        System.out.println("Bład! Wpisz cyfrę odpowiednią do typu wagonu który chcesz dodać");
//                    } else {
//                        if (operationalCarIsCargo && (Integer.parseInt(carType) > 8 || Integer.parseInt(carType) < 1)) {
//                            System.out.println("Bład! Wpisz cyfrę z listy");
//                        } else if (!operationalCarIsCargo && (Integer.parseInt(carType) > 4 || Integer.parseInt(carType) < 1))
//                            System.out.println("Bład! Wpisz cyfrę z listy");
//                        else {
//                            carTypeSucces = true;
//                        }
//                    }
//                }
//
//
//
//                System.out.println("Chcesz dodać jeszcze jeden wagon do tego pociągu?");
//                System.out.println("Wpisz t jeśli tak lub n jeśli nie");
//
//                boolean addMoreSucces = false;
//                while (!addMoreSucces) {
//                    String addMore = sc.nextLine();
//                    if (addMore.length() > 1 || addMore.length() < 1)
//                        System.out.println("Nie wygłupiaj się wpisz t lub n");
//                    else {
//                        if (addMore.charAt(0) == 't') {
//                            System.out.println("Wpisz numer kolenego wagonu który chcesz dodać");
//                            addMoreSucces = true;
//                        } else if (addMore.charAt(0) == 'n') {
//                            addCarsSuccess = true;
//                            addMoreSucces = true;
//                        } else {
//                            System.out.println("Nie wygłupiaj się wpisz t lub n");
//                        }
//                    }
//
//                }
//            }
//
//        }
//    }

//    public void trainAddCars() {
//        if (operationalCarIsCargo && carTypeSucces) {
//            switch (carType) {
//                case "1":
//                    addCar(new BasicCargoCar());
//                    break;
//                case "2":
//                    addCar(new ExplosiveMaterialCargoCar());
//                    break;
//                case "3":
//                    addCar(new GasMaterialCargoCar());
//                    break;
//                case "4":
//                    addCar(new HeavyCargoCar());
//                    break;
//                case "5":
//                    addCar(new LiquidMaterialCargoCar());
//                    break;
//                case "6":
//                    addCar(new LiquidToxicMaterialCargoCar());
//                    break;
//                case "7":
//                    addCar(new RefrigeratedCargoCar());
//                    break;
//                case "8":
//                    addCar(new ToxicMaterialCargoCar());
//                    break;
//                default:
//                    System.out.println("Err");
//                    break;
//
//            }
//        } else if (!operationalCarIsCargo && carTypeSucces) {
//            switch (carType) {
//                case "1":
//                    addCar(new LuggagePostCar());
//                    break;
//                case "2":
//                    addCar(new PassengerCar());
//                    break;
//                case "3":
//                    addCar(new PostCar());
//                    break;
//                case "4":
//                    addCar(new RestaurantCar());
//                    break;
//                default:
//                    System.out.println("Err");
//                    break;
//            }
//        }
//    }c


//    void addCar(String carName) {
//        try {
//            railSystem.trains.get(operationalTrainKey).carsNames.add(carName);
//            validateTrain();
//        } catch (TrainValidationException e) {
//            railSystem.trains.get(operationalTrainKey).carsNames.remove(carName);
//            System.out.println("Błąd: " + e.getMessage());
//        }
//    }

    void validateTrain() throws TrainValidationException {

        long carsNumber = railSystem.trains.get(operationalTrainKey).carsNames.size();

        List<Car> thisTrainElectricCars = new LinkedList<>();
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            if (entry.getValue().getTrainKey().equals(operationalTrainKey) && entry.getValue().needElectricity)
                thisTrainElectricCars.add(entry.getValue());
        }
        long electricCarsNumber = thisTrainElectricCars.size();

        int carsNetWeight = 0;
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            if (entry.getValue().getTrainKey().equals(operationalTrainKey)) {
                carsNetWeight += entry.getValue().getNetWeight();
            }
        }


        if (carsNetWeight > railSystem.locomotives.get(operationalLocomotiveName).getMaxPullWeight()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono uciąg lokomotywy"
                    + "\nMaksymalny uciąg to: " + railSystem.locomotives.get(operationalLocomotiveName).getMaxPullWeight()
                    + " ton a już bez tego wagonu jest: " + carsNetWeight + " ton"
            );
        } else if (carsNumber > railSystem.locomotives.get(operationalLocomotiveName).getMaxCarNumber()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono maksymalną ilość wagonów dla lokomotywy, czyli: "
                    + railSystem.locomotives.get(operationalLocomotiveName).getMaxCarNumber());
        } else if (electricCarsNumber > railSystem.locomotives.get(operationalLocomotiveName).getMaxElectricCarsNumber()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono maksymalną ilość wagonów wymagających " +
                    "podłączenia do sieci elektrycznej, czyli: " + railSystem.locomotives.get(operationalLocomotiveName).getMaxElectricCarsNumber());
        } else System.out.println("Wagon pomyślnie dodany do składu pociągu");

    }


    // test dodawania domyślnych wagonów

//    void setDefaultCarsToTrain() {
//        LuggagePostCar wagonBagazowoPocztowy1 = new LuggagePostCar();
//        PassengerCar wagonPasazerski1 = new PassengerCar();
//        PassengerCar wagonPasazerski2 = new PassengerCar();
//        PostCar wagonPocztowy1 = new PostCar();
//        RestaurantCar wagonRestauracyjny1 = new RestaurantCar();
//        BasicCargoCar prostyCiezarowyWagon = new BasicCargoCar();
//
//        addCar(wagonBagazowoPocztowy1);
//        addCar(wagonPasazerski1);
//        addCar(wagonPasazerski2);
//        addCar(wagonPocztowy1);
//        addCar(wagonRestauracyjny1);
//        addCar(prostyCiezarowyWagon);
//
//    }

    // newTrain potrzebne elementy (koniec)



    public void newLocomotive() {
        List<String> listedLocomotives = new LinkedList<>();
        for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
            listedLocomotives.add(entry.getKey());
        }
        System.out.println("Teraz tworzymy Lokomotywę na stacji: " + operationalStationName);

        System.out.println("Podaj nazwę dla lokomotywy");
        String name = " ";
        while (name.contains(" ") || name.length() < 2 || listedLocomotives.contains(name)) {
            name = sc.nextLine();
            if (name.contains(" ")) {
                System.out.println("Nazwa nie może zawierać spacji");
            }
            if (name.length() < 2)
                System.out.println("Nazwa powinna składać się z conajmniej dwuch znaków");
            if (listedLocomotives.contains(name)) {
                System.out.println("Lokomotywa o takiej nazwie już istnieje wymyśl inną nazwę");
            }
        }
        // tworzenie lokomowywy
        operationalLocomotiveName = name;
        railSystem.locomotives.put(name, new Locomotive(name, operationalStationName));
        System.out.println("Utworzono lokomotywę: " + operationalLocomotiveName + " na stacji: " + operationalStationName);

    }

    public void chooseCarType() {
        System.out.println("Czy ten wagon ma być ciężarowy");
        System.out.println("wpisz t jeśli tak lub n jeśli nie ");
        boolean cargoDecSucces = false;
        String cargoDec = "";

        while (!cargoDecSucces) {
            cargoDec = sc.nextLine();
            if (cargoDec.equals("t")) {
                operationalCarIsCargo = true;
                cargoDecSucces = true;
            } else if (cargoDec.equals("n")) {
                operationalCarIsCargo = false;
                cargoDecSucces = true;
            } else {
                System.out.println("Można wpisać tylko t lub n");
            }
        }
    }

    public void newCarNameInterface() {
        List<String> listedCars = new LinkedList<>();
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            listedCars.add(entry.getKey());
        }

        System.out.println("Podaj nazwę dla wagonu");

        operationalCarName = " ";
        while (operationalCarName.contains(" ") || operationalCarName.length() < 2 || listedCars.contains(operationalCarName)) {
            operationalCarName = sc.nextLine();
            if (operationalCarName.contains(" ")) {
                System.out.println("Nazwa nie może zawierać spacji");
            }
            if (operationalCarName.length() < 2)
                System.out.println("Nazwa powinna składać się z conajmniej dwuch znaków");
            if (listedCars.contains(operationalCarName)) {
                System.out.println("Wagon o takiej nazwie już istnieje wymyśl inną nazwę");
            }
        }
    }

    public void newCar() {

        newCarNameInterface();


        System.out.println("Wybierz typ wagonu wpisująć numer");
        if (!operationalCarIsCargo) {
            System.out.println("""
                    1.Wagon bagażowo-pocztowy
                    2.Wagon pasażerski
                    3.Wagon pocztowy
                    4.Wagon restauracyjny
                    """);
        } else if (operationalCarIsCargo) {
            System.out.println("""
                    1.Wagon towarowy podstawowy
                    2.Wagon na materiały wybuchowe
                    3.Wagon na materiały gazowe
                    4.Wagon towarowy ciężki
                    5.Wagon materiały ciekłe
                    6.Wagon na ciekłe materiały toksyczne
                    7.Wagon chłodniczy
                    8.Wagon na materiały toksyczne
                    """);
        }

        String carType = "";
        while (!carTypeSucces) {
            carType = sc.nextLine();
            if (carType.length() != 1) {
                System.out.println("Bład! Wpisz cyfrę odpowiednią do typu wagonu który chcesz dodać");
            } else if (!Character.isDigit(carType.charAt(0))) {
                System.out.println("Bład! Wpisz cyfrę odpowiednią do typu wagonu który chcesz dodać");
            } else {
                if (operationalCarIsCargo && (Integer.parseInt(carType) > 8 || Integer.parseInt(carType) < 1)) {
                    System.out.println("Bład! Wpisz cyfrę z listy");
                } else if (!operationalCarIsCargo && (Integer.parseInt(carType) > 4 || Integer.parseInt(carType) < 1))
                    System.out.println("Bład! Wpisz cyfrę z listy");
                else {
                    carTypeSucces = true;
                }
            }
        }
        if (operationalCarIsCargo) {
            switch (carType) {
                case "1":
                    railSystem.cars.put(operationalCarName, new BasicCargoCar(operationalStationName));
                    break;
                case "2":
                    railSystem.cars.put(operationalCarName, new ExplosiveMaterialCargoCar(operationalStationName));
                    break;
                case "3":
                    railSystem.cars.put(operationalCarName, new GasMaterialCargoCar(operationalStationName));
                    break;
                case "4":
                    railSystem.cars.put(operationalCarName, new HeavyCargoCar(operationalStationName));
                    break;
                case "5":
                    railSystem.cars.put(operationalCarName, new LiquidMaterialCargoCar(operationalStationName));
                    break;
                case "6":
                    railSystem.cars.put(operationalCarName, new LiquidToxicMaterialCargoCar(operationalStationName));
                    break;
                case "7":
                    railSystem.cars.put(operationalCarName, new RefrigeratedCargoCar(operationalStationName));
                    break;
                case "8":
                    railSystem.cars.put(operationalCarName, new ToxicMaterialCargoCar(operationalStationName));
                    break;
                default:
                    System.out.println("Err");
                    break;

            }
        } else if (!operationalCarIsCargo) {
            switch (carType) {
                case "1":
                    railSystem.cars.put(operationalCarName, new LuggagePostCar(operationalStationName));
                    break;
                case "2":
                    railSystem.cars.put(operationalCarName, new PassengerCar(operationalStationName));
                    break;
                case "3":
                    railSystem.cars.put(operationalCarName, new PostCar(operationalStationName));
                    break;
                case "4":
                    railSystem.cars.put(operationalCarName, new RestaurantCar(operationalStationName));
                    break;
                default:
                    System.out.println("Err");
                    break;
            }
        }

    }

    public void test(){
        railSystem.stations.put("NAZAR", new Station("NAZAR"));
        railSystem.cars.put("w1", new PassengerCar("NAZAR"));
        railSystem.locomotives.put("l1",new Locomotive("l1","NAZAR"));
    }


}







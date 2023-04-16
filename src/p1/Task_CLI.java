package p1;

import p1.Cars.*;
import p1.Cars.CargoCars.*;
import p1.Exeptions.TrainValidationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Task_CLI implements Runnable {
    RailSystem railSystem;

    String opStationName = "";
    String opPreviousStationName = " ";
    String opTrainKey = "";
    String oplLocomotiveName = "";
    String opCarName = "";
    boolean opCarIsCargo = false;
    String opLineKey = "";
    String opSourceStation = "";
    String opDestinationStation = "";
    LinkedList<String> opPathLines = new LinkedList<>();
    String opPathKey = "";

    boolean stopAddingLines = false;


    public Task_CLI(RailSystem railSystem) {
        this.railSystem = railSystem;
    }


    @Override
    public void run() {
//        System.out.println("hi");
        defaultRailSystemFill();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commandlist();
        while (true) {
            try {
                if (System.in.available() > 0) {
                    char c = (char) reader.read();
                    if (c == 'r') {
                        System.out.println("Wciśnąłeś r");
                        getTrainReport();
                        commandlist();
                    } else if (c == 's') {
                        System.out.println("Wciśnąłeś s");
                        newStation();
                        commandlist();
                    } else if (c == 't') {
                        System.out.println("Wciśnąłeś t");
                        System.out.println("Pociąg można stworzyć tylko na jakiejś stacji");
                        chooseStation();
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
                        System.out.println("Teraz tworzymy Wagon na stacji: " + opStationName);
                        chooseCarType();
                        newCar();
                        System.out.println("Wagon pomyślnie utworzony");
                        commandlist();
                    } else if (c == 'p') {
                        System.out.println("Wciśnąłeś p");
                        newPath();
                        commandlist();
                    } else if (c == 'j') {
                        System.out.println("Wciśnąłeś j");
                        System.out.println("Wybierz stację początkową");
                        chooseStation();
                        newPath();
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
                Thread.sleep(100);//100 ma byc finalnie
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Scanner sc = new Scanner(System.in);

    public void commandlist() {
        System.out.println("Lista poleceń:");
        System.out.println("r - (report) generowanie raportu dotyczącego konkretnego pociągu");
        System.out.println("s - (station) tworzenie nowej stacji");
        System.out.println("t - (train) tworzenie nowego pociągu");
        System.out.println("l - (locomotive) tworzenie nowej lokomortywy");
        System.out.println("c - (car) tworzenie nowego wagonu");
        System.out.println("p - (path) planowanie trasy pociągu");
        System.out.println("j - (join) tworzenie nowego połączenia między dwoma stacjami");
    }

    //  Raport (Report) /////////////////////
    public void getTrainReport() {
        System.out.println("Lista pociągów");
        List<String> trainKeys = new LinkedList<>();
        for (Map.Entry<String, Train> entry : railSystem.trains.entrySet()) {
            trainKeys.add(entry.getKey());
        }
        for (String train : trainKeys) {
            System.out.println(train);
        }
        String train = "";
        boolean selectTrainSucces = false;
        while (!selectTrainSucces) {
            System.out.println("Wpisz albo skopiuj wybrany pociąg");
            train = sc.nextLine();
            if (trainKeys.contains(train)) {
                selectTrainSucces = true;
            } else {
                if (train == "\n") {
                    System.out.println("kliknąłeś sam enter bez żadnej wartośći");
                }
                System.out.println("Bład, niema takiego pociągu");
            }
        }

        String lokomotywa = railSystem.trains.get(train).getLocomotiveName();
        int iloscWagonow = railSystem.trains.get(train).carsNames.size();
        String stacjaMacierzysta = railSystem.trains.get(train).getHomeStationName();


        System.out.println("Wybrałeś pociąg: " + train);
        System.out.println("Lokomotywa: " + lokomotywa);
        System.out.println("Ilość wagonów: " + iloscWagonow);
        System.out.println("Stacja macierzysta: " + stacjaMacierzysta);
        System.out.println("Wagony:");
        railSystem.trains.get(train).showCars();


    }

    //  Stacja (Station) /////////////////////
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
                    opStationName = key;
                    System.out.println("Utworzyłeś nową stację: " + opStationName);
                    keySuccess = true;

                } else {
                    System.out.println("Nazwa stacji powinna być pisana dużymi literami \nbez spacji, liczb i innych znaków");
                    System.out.println("Podaj nazwę stacji");
                }
            }


        }


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
                    opStationName = stationDecision;
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

    //  Pociąg  (Train) /////////////////////
    public void newTrain() {
        System.out.println("Teraz tworzymy pociąg na stacji: " + opStationName);
        trainKeyInterface();

        System.out.println("Dodajmy lokomotywę do naszego pociągu");
        chooseLocomotive();
        railSystem.trains.get(opTrainKey).setlocomotiveName(oplLocomotiveName);
        railSystem.locomotives.get(oplLocomotiveName).setHomeStation("");


        System.out.println("To teraz dodajemy wagony do lokomotywy: "
                + oplLocomotiveName + " na stacji: " + opStationName);

        trainAddCarInterface();

        System.out.println("Twój pociąg: ");
        System.out.println(railSystem.trains.get(opTrainKey));
        System.out.println("Wagony: ");
        railSystem.trains.get(opTrainKey).showCars();
        System.out.println("Pociąg utworzony poprawnie, podaj następne polecenie");


    }

    public void chooseTrain() {
        boolean trainDecisionSucces = false;
        boolean ifNoTrainSucces = false;

        System.out.println("Jeśli chcesz utworzyć nowy pociąg to wpisz: nt ");
        System.out.println("Jeśli chcesz wybrac pociąg z listy dostępnych na tej stacji to wpisz: lt ");

        while (!trainDecisionSucces) {
            String trainDecision = "";
            trainDecision = sc.nextLine();
            if (trainDecision.equals("lt")) {
                //tworzę tymczasową listę pociągów na stacji
                List<String> listedTrains = new LinkedList<>();
                listedTrains.clear();
                for (Map.Entry<String, Train> entry : railSystem.trains.entrySet()) {
                    if (entry.getValue().getHomeStationName().equals(opStationName))
                        listedTrains.add(entry.getKey());
                }
                // wyswietlam listę pociągów
                if (listedTrains.size() == 0) {
                    System.out.println("Na tej stacji niema pociągów ");
                    chooseTrain();
                    for (Map.Entry<String, Train> entry : railSystem.trains.entrySet()) {
                        if (entry.getValue().getHomeStationName().equals(opStationName))
                            listedTrains.add(entry.getKey());
                    }
                    trainDecision = opTrainKey;
                    ifNoTrainSucces = true;
                } else {
                    System.out.println("Lista pociągów:");
                    for (String train : listedTrains) {
                        System.out.println(train);
                    }
                }
                if (!ifNoTrainSucces) {
                    System.out.println("Wpisz albo skopiuj nazwę pociągu dla którego chcesz zaplanować trasę");
                    // walidacja nazwy pociąga
                    while (!listedTrains.contains(trainDecision)) {
                        trainDecision = sc.nextLine();
                        if (!listedTrains.contains(trainDecision)) {
                            System.out.println("Wybrany przez ciebie pociąg nie istnieje");
                        }
                    }
                }
                // sukces:
                if (listedTrains.contains(trainDecision)) {
                    trainDecisionSucces = true;
                    opTrainKey = trainDecision;
                }
            } else if (trainDecision.equals("nt")) {
                System.out.println("Tworzymy nowy pociąg");
                newTrain();
                trainDecisionSucces = true;
            } else {
                System.out.println("poprawny zapis to 2 małe litery lt lub nt, popraw bład");
            }

        }
    }

    public void trainKeyInterface() {
        String key = "";
        boolean keySuccess = false;
        // Interfejs klucza pociągu
        System.out.println("Podaj identyfikator pociągu (klucz)");
        System.out.println();
        System.out.println("""
                Przykład:
                Cargo train: CT_0001 - (pociąg ciężarowy)
                Passenger train: PT_0001 - (pociąg osobowy)
                                
                                                
                Uwaga! Pierwsze 2 litery są bardzo ważne od nich zależy jakiego typu będzie to pociąg.
                Bagaż i pocztę można przewozić tylko pociągami osobowymi
                *Wskazówka - pierwsze 3 zera są do twojej dyspozycji :)
                """);

        while (!keySuccess) {
            //tymczasowa lista nazw pociągów
            List<String> trainKeys = new ArrayList<>();
            for (Map.Entry<String, Train> entry : railSystem.trains.entrySet()) {
                trainKeys.add(entry.getKey());
            }

            key = sc.nextLine();
            System.out.println();

            // walidacja do pola key
            if (key.length() != 7 || !key.contains("_"))
                System.out.println("Klucz który podałeś nie jest odpowiedni, poprawny format zapisu to : PT_#### lub CT_####");
            else if (!Character.isUpperCase(key.charAt(0)) && !Character.isUpperCase(key.charAt(1))) {
                System.out.println("Dwie pierwsze litery powinne być duże i w czytelny sposób reprezentować typ pociągu" +
                        " na przykład PT - Passenger Train");
            } else if (!Character.isDigit(key.charAt(3)) && !Character.isDigit(key.charAt(4))
                    && !Character.isDigit(key.charAt(5)) && !Character.isDigit(key.charAt(6))) {
                System.out.println("Cztery ostatnie znaki powinny być liczbami np: CT_0001");
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
                railSystem.trains.get(key).setHomeStationName(opStationName);
                if (key.charAt(0) == 'C') {
                    opCarIsCargo = true;
                } else if (key.charAt(0) == 'P') {
                    opCarIsCargo = false;
                }
                keySuccess = true;
                opTrainKey = key;
                System.out.println("Udało się stworzyłeś pociąg");
            }
        }
    }

    public void trainAddCarInterface() {
        boolean addCarSucces = false;

        while (!addCarSucces) {
            chooseCar();
            String prevHomeStation = railSystem.cars.get(opCarName).getHomeStation();
            try {
                railSystem.cars.get(opCarName).setHomeStation("");
                railSystem.cars.get(opCarName).setTrainKey(opTrainKey);
                railSystem.trains.get(opTrainKey).carsNames.add(opCarName);
                validateTrain();
                trainLoadCarInterface();
                validateTrain();
                addCarSucces = true;
            } catch (TrainValidationException e) {
                railSystem.cars.get(opCarName).setHomeStation(prevHomeStation);
                railSystem.cars.get(opCarName).setTrainKey("");
                railSystem.trains.get(opTrainKey).carsNames.remove(opCarName);
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
                    trainAddCarInterface();
                } else if (addMore.equals("n")) {
                    addMoreSucces = true;
                } else {
                    System.out.println("Nie wygłupiaj się wpisz t lub n");
                }
            }
        }
    }

    public void trainLoadCarInterface() {
        System.out.println("Dodajmy teraz ładunek do wagonu");
        boolean currentCarIsCargo = railSystem.cars.get(opCarName).isCargo();

        if (currentCarIsCargo) {
            System.out.println("To jest wagon ciężarowy, podaj masę ładunku w tonach");
            boolean addWeightSucces = false;
            while (!addWeightSucces) {
                String weight = sc.nextLine();
                if (weight.length() <= 2 && weight.matches("\\d+")) {
                    if (weight.equals(0)) {
                        System.out.println("Rozumiem ten wagon ma jechcać bez ładunku");
                    }
                    try {
                        railSystem.cars.get(opCarName).incGrossWeight(Integer.parseInt(weight));
                        validateTrain();
                        addWeightSucces = true;
                        System.out.println("Sukces");
                    } catch (TrainValidationException e) {
                        int carNetWeight = railSystem.cars.get(opCarName).getNetWeight();
                        railSystem.cars.get(opCarName).setGrossWeight(carNetWeight);
                        System.out.println("Błąd: " + e.getMessage());
                    }
                } else {
                    System.out.println("Masa ładunku może być maksymalnie 2 cyfrowa i nie może zawierać innyc znaków");
                }

            }
        } else {
            System.out.println("To jest wagon osobowy");
            System.out.println("W przypadku przewozu osób na pokładzie można przyjąć następujące założenie: ");
            System.out.println("Średnio ładunek wagonu pasażerskiego przy zajętych wszystkich miesjcach " +
                    "wynosi 10 ton (uwzględniająć bagaże podrózujących osób) ");
            boolean addWeightSucces = false;
            while (!addWeightSucces) {
                System.out.println("Podaj masę ładunku w tonach");
                String weight = sc.nextLine();
                if (weight.length() <= 2 && weight.matches("\\d+")) {
                    if (weight.equals(0)) {
                        System.out.println("Rozumiem ten wagon ma jechcać bez ładunku");
                    }
                    try {
                        railSystem.cars.get(opCarName).incGrossWeight(Integer.parseInt(weight));
                        validateTrain();
                        addWeightSucces = true;
                        System.out.println("Sukces");
                    } catch (TrainValidationException e) {
                        int carNetWeight = railSystem.cars.get(opCarName).getNetWeight();
                        railSystem.cars.get(opCarName).setGrossWeight(carNetWeight);
                        System.out.println("Błąd: " + e.getMessage());
                    }
                } else {
                    System.out.println("Masa ładunku może być maksymalnie 2 cyfrowa i nie może zawierać innyc znaków");
                }

            }
        }
    }

    void validateTrain() throws TrainValidationException {

        long carsNumber = railSystem.trains.get(opTrainKey).carsNames.size();

        List<Car> thisTrainElectricCars = new LinkedList<>();
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            if (entry.getValue().getTrainKey().equals(opTrainKey) && entry.getValue().needElectricity)
                thisTrainElectricCars.add(entry.getValue());
        }
        long electricCarsNumber = thisTrainElectricCars.size();

        int carsNetWeight = 0;
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            if (entry.getValue().getTrainKey().equals(opTrainKey)) {
                carsNetWeight += entry.getValue().getNetWeight();
            }
        }
        int carsGrossWeight = 0;
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            if (entry.getValue().getTrainKey().equals(opTrainKey)) {
                carsGrossWeight += entry.getValue().getGrossWeight();
            }
        }

        if (carsNetWeight > railSystem.locomotives.get(oplLocomotiveName).getMaxPullWeight()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono uciąg lokomotywy"
                    + "\nMaksymalny uciąg to: " + railSystem.locomotives.get(oplLocomotiveName).getMaxPullWeight()
                    + " ton a z tym wagonem było by: " + carsNetWeight + " ton"
            );
        } else if (carsGrossWeight > railSystem.locomotives.get(oplLocomotiveName).getMaxPullWeight()) {
            throw new TrainValidationException("Nie udało się załadować tego wagonu, przekroczono uciąg lokomotywy"
                    + "\nMaksymalny uciąg to: " + railSystem.locomotives.get(oplLocomotiveName).getMaxPullWeight()
                    + " ton a z tym ładunkiem było by: " + carsGrossWeight + " ton"
            );
        } else if (carsNumber > railSystem.locomotives.get(oplLocomotiveName).getMaxCarNumber()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono maksymalną ilość wagonów dla lokomotywy, czyli: "
                    + railSystem.locomotives.get(oplLocomotiveName).getMaxCarNumber());
        } else if (electricCarsNumber > railSystem.locomotives.get(oplLocomotiveName).getMaxElectricCarsNumber()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono maksymalną ilość wagonów wymagających " +
                    "podłączenia do sieci elektrycznej, czyli: " + railSystem.locomotives.get(oplLocomotiveName).getMaxElectricCarsNumber());
        } else System.out.println("Wagon pomyślnie dodany do składu pociągu");

    }

    //  Lokomotywa /////////////////////
    public void newLocomotive() {
        List<String> listedLocomotives = new LinkedList<>();
        for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
            listedLocomotives.add(entry.getKey());
        }
        System.out.println("Teraz tworzymy Lokomotywę na stacji: " + opStationName);

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
        oplLocomotiveName = name;
        railSystem.locomotives.put(name, new Locomotive(name, opStationName));
        System.out.println("Utworzono lokomotywę: " + oplLocomotiveName + " na stacji: " + opStationName);

    }

    public void chooseLocomotive() {
        boolean locomotiveDecisionSucces = false;
        boolean ifNoLocomotivesSucces = false;

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
                    if (entry.getValue().getHomeStation().equals(opStationName))
                        listedLocomotives.add(entry.getKey());
                }
                // wyswietlam listę lokomotyw
                if (listedLocomotives.size() == 0) {
                    System.out.println("Na tej stacji niema lokomotyw ");
                    chooseLocomotive();
                    for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
                        if (entry.getValue().getHomeStation().equals(opStationName))
                            listedLocomotives.add(entry.getKey());
                    }
                    locoDecision = oplLocomotiveName;
                    ifNoLocomotivesSucces = true;
                } else {
                    System.out.println("Lista lokomotyw:");
                    for (String locomotive : listedLocomotives) {
                        System.out.println(locomotive);
                    }
                }
                if (!ifNoLocomotivesSucces) {
                    System.out.println("Wpisz albo skopiuj nazwę lokomotywy którą chcesz dodać do pociągu");
                    // walidacja nazwy lokomotywy
                    while (!listedLocomotives.contains(locoDecision)) {
                        locoDecision = sc.nextLine();
                        if (!listedLocomotives.contains(locoDecision)) {
                            System.out.println("Wybrana przez ciebie lokomotywa nie istnieje");
                        }
                    }
                }
                // sukces:
                if (listedLocomotives.contains(locoDecision)) {
                    locomotiveDecisionSucces = true;
                    oplLocomotiveName = locoDecision;
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

    //  Wagon (Car) /////////////////////
    public void newCar() {
        boolean carTypeSucces = false;
        newCarNameInterface();

        System.out.println("Wybierz typ wagonu wpisująć numer");
        if (!opCarIsCargo) {
            System.out.println("""
                    1.Wagon bagażowo-pocztowy
                    2.Wagon pasażerski
                    3.Wagon pocztowy
                    4.Wagon restauracyjny
                    """);
        } else if (opCarIsCargo) {
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
                if (opCarIsCargo && (Integer.parseInt(carType) > 8 || Integer.parseInt(carType) < 1)) {
                    System.out.println("Bład! Wpisz cyfrę z listy");
                } else if (!opCarIsCargo && (Integer.parseInt(carType) > 4 || Integer.parseInt(carType) < 1))
                    System.out.println("Bład! Wpisz cyfrę z listy");
                else {
                    carTypeSucces = true;
                }
            }
        }
        if (opCarIsCargo) {
            switch (carType) {
                case "1":
                    railSystem.cars.put(opCarName, new BasicCargoCar(opStationName));
                    break;
                case "2":
                    railSystem.cars.put(opCarName, new ExplosiveMaterialCargoCar(opStationName));
                    break;
                case "3":
                    railSystem.cars.put(opCarName, new GasMaterialCargoCar(opStationName));
                    break;
                case "4":
                    railSystem.cars.put(opCarName, new HeavyCargoCar(opStationName));
                    break;
                case "5":
                    railSystem.cars.put(opCarName, new LiquidMaterialCargoCar(opStationName));
                    break;
                case "6":
                    railSystem.cars.put(opCarName, new LiquidToxicMaterialCargoCar(opStationName));
                    break;
                case "7":
                    railSystem.cars.put(opCarName, new RefrigeratedCargoCar(opStationName));
                    break;
                case "8":
                    railSystem.cars.put(opCarName, new ToxicMaterialCargoCar(opStationName));
                    break;
                default:
                    System.out.println("Err");
                    break;

            }
        } else if (!opCarIsCargo) {
            switch (carType) {
                case "1":
                    railSystem.cars.put(opCarName, new LuggagePostCar(opStationName));
                    break;
                case "2":
                    railSystem.cars.put(opCarName, new PassengerCar(opStationName));
                    break;
                case "3":
                    railSystem.cars.put(opCarName, new PostCar(opStationName));
                    break;
                case "4":
                    railSystem.cars.put(opCarName, new RestaurantCar(opStationName));
                    break;
                default:
                    System.out.println("Err");
                    break;
            }
        }

    }

    public void newCarNameInterface() {
        List<String> listedCars = new LinkedList<>();
        for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
            listedCars.add(entry.getKey());
        }

        System.out.println("Podaj nazwę dla wagonu");

        opCarName = " ";
        while (opCarName.contains(" ") || opCarName.length() < 2 || listedCars.contains(opCarName)) {
            opCarName = sc.nextLine();
            if (opCarName.contains(" ")) {
                System.out.println("Nazwa nie może zawierać spacji");
            }
            if (opCarName.length() < 2)
                System.out.println("Nazwa powinna składać się z conajmniej dwuch znaków");
            if (listedCars.contains(opCarName)) {
                System.out.println("Wagon o takiej nazwie już istnieje wymyśl inną nazwę");
            }
        }
    }

    public void chooseCarType() {
        System.out.println("Czy ten wagon ma być ciężarowy");
        System.out.println("wpisz t jeśli tak lub n jeśli nie ");
        boolean cargoDecSucces = false;
        String cargoDec = "";

        while (!cargoDecSucces) {
            cargoDec = sc.nextLine();
            if (cargoDec.equals("t")) {
                opCarIsCargo = true;
                cargoDecSucces = true;
            } else if (cargoDec.equals("n")) {
                opCarIsCargo = false;
                cargoDecSucces = true;
            } else {
                System.out.println("Można wpisać tylko t lub n");
            }
        }
    }

    public void chooseCar() {
        boolean carDecisionSucces = false;
        boolean ifNoCarsSucces = false;

        System.out.println("Jeśli chcesz utworzyć nowy wagon to wpisz: nw ");
        System.out.println("Jeśli chcesz wybrac wagon z listy dostępnych na tej stacji to wpisz: lw ");

        while (!carDecisionSucces) {
            boolean isTrainCargo = railSystem.trains.get(opTrainKey).isCargo();

            String carDecision = "";
            carDecision = sc.nextLine();
            if (carDecision.equals("lw")) {
                //tworzę tymczasową listę wagonów na stacji
                List<String> listedCars = new LinkedList<>();
                for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
                    if (isTrainCargo && entry.getValue().getHomeStation().equals(opStationName) && entry.getValue().isCargo())
                        listedCars.add(entry.getKey());
                    if (!isTrainCargo && entry.getValue().getHomeStation().equals(opStationName) && !entry.getValue().isCargo())
                        listedCars.add(entry.getKey());
                }
                // wyswietlam listę wagonów
                if (listedCars.size() == 0) {
                    System.out.println("Na tej stacji niema nieprzydzielonych wagonów");
                    chooseCar();
                    for (Map.Entry<String, Car> entry : railSystem.cars.entrySet()) {
                        if (isTrainCargo && entry.getValue().getHomeStation().equals(opStationName) && entry.getValue().isCargo())
                            listedCars.add(entry.getKey());
                        if (!isTrainCargo && entry.getValue().getHomeStation().equals(opStationName) && !entry.getValue().isCargo())
                            listedCars.add(entry.getKey());
                    }
                    carDecision = opCarName;
                    ifNoCarsSucces = true;
                } else {
                    System.out.println("Lista wagonów:");
                    for (String car : listedCars) {
                        System.out.println(car);
                    }
                }

                if (!ifNoCarsSucces) {
                    System.out.println("Wpisz albo skopiuj nazwę wagonu który chcesz dodać do pociągu");
                    // walidacja nazwy wagonu
                    while (!listedCars.contains(carDecision)) {
                        carDecision = sc.nextLine();
                        if (!listedCars.contains(carDecision)) {
                            System.out.println("Wybrany przez ciebie wagon nie istnieje");
                        }
                    }
                }
                // sukces:
                if (listedCars.contains(carDecision)) {
                    carDecisionSucces = true;
                    opCarName = carDecision;
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

    //  Trasa (Path) /////////////////////
    public void newPath() {

        System.out.println("Wybierz stację od której chcesz zacząć");
        chooseStation();

        opSourceStation = opStationName;
        System.out.println("Teraz wybierz pociąg dla którego chcesz zaplanować trasę");
        chooseTrain();
        oplLocomotiveName = railSystem.trains.get(opTrainKey).getLocomotiveName();

        // stacja początkowa lokomotywy - ustalamy
        railSystem.locomotives.get(oplLocomotiveName).setSourceStation(opStationName);
        System.out.println("Teraz powinniśmy po kolei zaplanować całą trasę dla naszego pociągu");

        while (!stopAddingLines) {
            List<String> listedLines = new LinkedList<>();
            for (Map.Entry<String, Line> entry : railSystem.lines.entrySet()) {
                // wyświetlą się tylko te linie które prowadzą dalej - nie można się cofnąć
                if (entry.getKey().startsWith(opStationName) && !entry.getKey().endsWith(opPreviousStationName)) {
                    listedLines.add(entry.getKey());
                }
            }
            if (listedLines.size() == 0) {
                System.out.println("Od tej stacji nie wychodzi żadna linia ");
                System.out.println("Jeśli chcesz aby ta stacja była stacją końcową wpisz t");
                System.out.println("Jeśli chcesz utworzyć nową linię to wpisz n");
                boolean addMoreSucces = false;
                while (!addMoreSucces) {
                    String addMore = sc.nextLine();
                    if (addMore.length() > 1 || addMore.length() < 1)
                        System.out.println("Nie wygłupiaj się wpisz t lub n");
                    else {
                        if (addMore.equals("t")) {
                            addMoreSucces = true;
                            stopAddingLines = true;
                            opPathLines.add(opLineKey);
                            addPath();

                            System.out.println("Trasa pomyślnie dodana");
                        } else if (addMore.equals("n")) {
                            String oldStation = opStationName;
                            newLine();
                            opStationName = oldStation;
                            addMoreSucces = true;

                        } else {
                            System.out.println("Nie wygłupiaj się wpisz t lub n");
                        }
                    }
                }

            } else {
                chooseLine();
                // dodajemy linię
                opPathLines.add(opLineKey);
                opPreviousStationName = opStationName;
                opStationName = opLineKey.substring(opLineKey.indexOf("_") + 1);

                System.out.println("Linia pomyślnie dodana do tej trasy, chcesz dodać jeszcze jedną?");
                System.out.println("Wpisz t jeśli tak lub n jeśli nie");
                boolean addMoreSucces = false;

                while (!addMoreSucces) {
                    String addMore = sc.nextLine();
                    if (addMore.length() > 1 || addMore.length() < 1)
                        System.out.println("Nie wygłupiaj się wpisz t lub n");
                    else {
                        if (addMore.equals("t")) {
                            addMoreSucces = true;
                        } else if (addMore.equals("n")) {
                            //koniec
                            addPath();
                            System.out.println("Pociąg: " + railSystem.paths.get(opPathKey).trainsKeys);
                            System.out.println("Trasa: " + railSystem.paths.get(opPathKey).getPathKey());
                            System.out.println("Linie :");
                            for (String line : railSystem.paths.get(opPathKey).linesKeys) {
                                System.out.println(line);
                            }

                            System.out.println("Trasa pomyślnie dodana");
                            addMoreSucces = true;
                            stopAddingLines = true;
                        } else {
                            System.out.println("Nie wygłupiaj się wpisz t lub n");
                        }
                    }
                }
            }
        }
    }

    public void chooseLine() {
        boolean lineDecisionSucces = false;
        //tworzę tymczasową listę linii ze stacji na której jesteśmy
        List<String> listedLines = new LinkedList<>();
        for (Map.Entry<String, Line> entry : railSystem.lines.entrySet()) {
            // wyświetlą się tylko te linie które prowadzą dalej - nie można się cofnąć
            if (entry.getKey().startsWith(opStationName) && !entry.getKey().endsWith(opPreviousStationName))
                listedLines.add(entry.getKey());
        }
        System.out.println("Lista linii:");
        for (String line : listedLines) {
            System.out.println(line);
        }
        System.out.println("Jeśli chcesz utworzyć nową linię na tej stacji to wpisz: nl ");
        System.out.println("Jeśli chcesz wybrac linię z listy dostępnych na tej stacji ");
        System.out.println("to skopiuj lub wpisz nazwę tej linii ");
        while (!lineDecisionSucces) {
            String lineDecision = "";
            lineDecision = sc.nextLine();
            if (lineDecision.equals("nl")) {
                System.out.println("Tworzymy nową linię na stacji: " + opStationName);
//                String oldStation = opStationName;
                newLine();
//                opStationName = oldStation;
                lineDecisionSucces = true;
            } else if (listedLines.contains(lineDecision)) {
                opLineKey = lineDecision;
                lineDecisionSucces = true;
            } else {
                System.out.println("poprawny zapis to 2 małe litery nl lub nazwa jakiejś lini z listy, popraw bład");
            }
        }


    }

    public void addPath() {
        // stacja końcowa lokomotywy - ustalamy
        railSystem.locomotives.get(oplLocomotiveName).setDestinationStation(opStationName);
        String pathKey = opSourceStation + "__" + opStationName;
        opPathKey = pathKey;
        // tworzymy obiekt path
        railSystem.paths.put(pathKey, new Path(pathKey, opPathLines));
        railSystem.paths.get(pathKey).trainsKeys.add(opTrainKey);
    }

    public void reversePath() {
        List<String> currentList = railSystem.paths.get(opPathKey).linesKeys;
        String newStartStation = "";
        String newEndStation = "";

        List<String> reversedList = new LinkedList<>();
        for (int i = currentList.size() - 1; i >= 0; i--) {
            String line = currentList.get(i);
            String[] cities = line.split("_");
            reversedList.add(cities[1] + "_" + cities[0]);
            if (i == currentList.size() - 1)
                newStartStation = cities[1];
            if (i == 0)
                newEndStation = cities[0];

        }
        String newPathKey = newStartStation + "__" + newEndStation;
        Path oldValue = railSystem.paths.get(opPathKey);
        railSystem.paths.remove(opPathKey);
        railSystem.paths.put(newPathKey, oldValue);
        railSystem.paths.get(newPathKey).setPathKey(newPathKey);
        opPathKey = newPathKey;
        railSystem.paths.get(opPathKey).linesKeys = reversedList;

    }

    //  Linia (Line) /////////////////////
    public void newLine() {
        opPreviousStationName = opStationName;
        String startStation = "";
        String endStation = "";
        List<String> listedLines = new LinkedList<>();
        for (Map.Entry<String, Line> entry : railSystem.lines.entrySet()) {
            if (entry.getKey().startsWith(opStationName)) {
                listedLines.add(entry.getKey());
            }
        }
        boolean endStationSucces = false;
        startStation = opStationName;
        System.out.println("Wybierz stację docelową");
        while (!endStationSucces) {
            chooseStation();
            String tmpEndStation = opStationName;
            String tmpLineKey = startStation + "_" + tmpEndStation;
            if (listedLines.contains(tmpLineKey)) {
                System.out.println("Takie połaczenie już istnieje");
            } else {
                endStation = tmpEndStation;
                endStationSucces = true;
            }
        }
        String newLineKey = startStation + "_" + endStation;
        railSystem.lines.put(newLineKey, new Line(startStation, endStation));
        opStationName = endStation;
        opLineKey = newLineKey;
        System.out.println("Linia poprawnie utworzona");
    }


    public void defaultRailSystemFill() {

        int stationsSize = railSystem.stations.size();
        int pTrainNumber = 1;
        int cTrainNumber = 1;
        int locoNumber = 1;
        int carNumber = 1;

        for (Map.Entry<String, Station> entry : railSystem.stations.entrySet()) {
            // tworzę 2 pociągi na każdej stacji 1 osobowy i 1 ciężarowy
            for (int i = 0; i < 2; i++) {
                // Passenger train
                String ptTrainName = "PT_000" + (pTrainNumber++);
                railSystem.trains.put(ptTrainName, new Train(ptTrainName));
                railSystem.trains.get(ptTrainName).setHomeStationName(entry.getKey());
                String ptLocoName = "l" + (locoNumber++);
                railSystem.locomotives.put(ptLocoName, new Locomotive(ptLocoName, entry.getKey()));
                railSystem.trains.get(ptTrainName).setlocomotiveName(ptLocoName);
                // do każdego pociągu 5 wagonów
                for (int j = 0; j < 5; j++) {
                    String pCarName = "c" + (carNumber++);
                    railSystem.cars.put(pCarName, new PassengerCar(entry.getKey()));
                    railSystem.cars.get(pCarName).setHomeStation(entry.getKey());
                    railSystem.cars.get(pCarName).setTrainKey(ptTrainName);
                    railSystem.trains.get(ptTrainName).carsNames.add(pCarName);

                }
                // Cargo train
                String ctTrainName = "CT_000" + (cTrainNumber++);
                railSystem.trains.put(ctTrainName, new Train(ctTrainName));
                railSystem.trains.get(ctTrainName).setHomeStationName(entry.getKey());
                String ctLocoName = "l" + (locoNumber++);
                railSystem.locomotives.put(ctLocoName, new Locomotive(ctLocoName, entry.getKey()));
                railSystem.trains.get(ctTrainName).setlocomotiveName(ctLocoName);
                // do każdego pociągu 5 wagonów
                for (int j = 0; j < 5; j++) {
                    String cCarName = "c" + (carNumber++);
                    railSystem.cars.put(cCarName, new PassengerCar(entry.getKey()));
                    railSystem.cars.get(cCarName).setHomeStation(entry.getKey());
                    railSystem.cars.get(cCarName).setTrainKey(ctTrainName);
                    railSystem.trains.get(ctTrainName).carsNames.add(cCarName);

                }

            }
        }
    }

}


// trasa zatoczyła koło
//            if (opStationName.equals(opSourceStation)) {
//                System.out.println("Została wybrana linia która doprawadziła cię do stacji początkowej");
//                // stacja końcowa lokomotywy - ustalamy
//                railSystem.locomotives.get(oplLocomotiveName).setDestinationStation(opStationName);
//                String pathKey = opSourceStation + "__" + opStationName;
//                // tworzymy obiekt path
//                railSystem.paths.put(pathKey, new Path(pathKey, opPathLines));
//                railSystem.paths.get(pathKey).trainsKeys.add(opTrainKey);
//                stopAddingLines = true;
//                System.out.println("Trasa zatoczyła koło, kończymy dodawanie trasy");
//            } else {






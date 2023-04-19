package p1;

import p1.Cars.*;
import p1.Cars.CargoCars.*;
import p1.Exeptions.TrainValidationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Task_CLI implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();

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


    public Task_CLI() {
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        commandlist();
        while (true) {
            try {
                if (System.in.available() > 0) {// sprawdzenie czy przycisk jest wciśnięty
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
                    } else if (c == 'd') {
                        System.out.println("Wciśnąłeś d");
                        remove();
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
        System.out.println("d - (delete) usuwanie obiektów");
    }

    //  Raport (Report) /////////////////////
    public void getTrainReport() {


        System.out.println("Lista pociągów");
        List<String> trainKeys = new LinkedList<>(railSystem.getTrainsKeys());
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

        String locomotive = railSystem.getLocoNameByTrain(train);
        int carsCount = railSystem.getCarsCountByTrain(train);



        double distancePassedPercantageTotal = railSystem.getLocomotive(locomotive).getDistancePassedPercantageTotal();
        double distancePassedPercantageLocal = railSystem.getLocomotive(locomotive).getDistancePassedPercantageLocal();
        String homeStationName = railSystem.getTrain(train).getHomeStationName();
        List<String> carNames = new LinkedList<>(railSystem.getCarsKeysSortedByCarWeight(train));
        boolean isMoving = railSystem.getLocomotive(locomotive).isMoving();

        System.out.println("Wybrałeś pociąg: " + train);
        System.out.println("Pociąg pokonał: " + distancePassedPercantageTotal + " procent całej trasy");
        System.out.println("Pociąg pokonał: " + distancePassedPercantageLocal + " procent lokalnej trasy");
        System.out.println("Lokomotywa: " + locomotive);
        System.out.println("Ilość wagonów: " + carsCount);
        System.out.println("Stacja macierzysta: " + homeStationName);
        System.out.println("Wagony:");
        for (String carName : carNames) {
            System.out.println(carName);
        }
        if (isMoving) {
            String line = railSystem.getLocomotive(locomotive).currentLineKey.orElse("");
            String[] cities = line.split("_");
            System.out.println("Pociąg jedize do stacji: " + cities[1]);
        } else {
            String station = String.valueOf(railSystem.getLocomotive(locomotive).currentStation);
            System.out.println("Pociąg stoi na stacji: " + station);
        }
        System.out.println("Lokomotywa: " + locomotive);
        System.out.println("Stacja macierzysta: " + homeStationName);

    }

    //  Stacja (Station) /////////////////////
    public void newStation() {
        Scanner sc = new Scanner(System.in);
        boolean keySuccess = false;
        //tworzę tymczasową listę stacji
        List<String> listedStations = new LinkedList<>(railSystem.getStrationsKeys());

        System.out.println("Nazwa stacji powinna być pisana dużymi literami \nbez spacji, liczb i innych znaków");
        System.out.println("Podaj nazwę stacji");
        while (!keySuccess) {
            String key = sc.nextLine();
            if (listedStations.contains(key)) {
                System.out.println("Taka stacja już istnieje wymyśl inną nazwę dla nowej stacji");
            } else {
                if (key.matches("[A-Z]+")) {
                    railSystem.addStation(key);
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
                List<String> listedStations = new LinkedList<>(railSystem.getStrationsKeys());

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
        railSystem.getTrain(opTrainKey).setlocomotiveName(oplLocomotiveName);
        railSystem.getLocomotive(oplLocomotiveName).setHomeStation("");
        railSystem.getLocomotive(oplLocomotiveName).setTrainKey(opTrainKey);

        System.out.println("To teraz dodajemy wagony do lokomotywy: "
                + oplLocomotiveName + " na stacji: " + opStationName);

        trainAddCarInterface();

        System.out.println("Twój pociąg: ");
        System.out.println(railSystem.getTrain(opTrainKey));
        System.out.println("Wagony: ");
        railSystem.getTrain(opTrainKey).showCars();
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
                List<Train> allTrainsValues = new LinkedList<>(railSystem.getTrainsValues());
                listedTrains.clear();

                for (Train trainsValue : allTrainsValues) {
                    if (trainsValue.getHomeStationName().equals(opStationName))
                        listedTrains.add(trainsValue.getKey());
                }
                // wyswietlam listę pociągów
                if (listedTrains.size() == 0) {
                    System.out.println("Na tej stacji niema pociągów ");
                    chooseTrain();
                    for (Train trainsValue : allTrainsValues) {
                        if (trainsValue.getHomeStationName().equals(opStationName))
                            listedTrains.add(trainsValue.getKey());
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
            List<String> trainKeys = new ArrayList<>(railSystem.getTrainsKeys());


            key = sc.nextLine();

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
                railSystem.addTrain(key, opStationName);
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
            String prevHomeStation = railSystem.getCar(opCarName).getHomeStation();
            try {
                railSystem.getCar(opCarName).setHomeStation("");
                railSystem.getCar(opCarName).setTrainKey(opTrainKey);
                railSystem.getTrain(opTrainKey).carsNames.add(opCarName);
                validateTrain();
                trainLoadCarInterface();
                validateTrain();
                addCarSucces = true;
            } catch (TrainValidationException e) {
                railSystem.getCar(opCarName).setHomeStation(prevHomeStation);
                railSystem.getCar(opCarName).setTrainKey("");
                railSystem.getTrain(opTrainKey).carsNames.remove(opCarName);
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
        boolean currentCarIsCargo = railSystem.getCar(opCarName).isCargo();

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
                        railSystem.getCar(opCarName).incGrossWeight(Integer.parseInt(weight));
                        validateTrain();
                        addWeightSucces = true;
                        System.out.println("Sukces");
                    } catch (TrainValidationException e) {
                        int carNetWeight = railSystem.getCar(opCarName).getNetWeight();
                        railSystem.getCar(opCarName).setGrossWeight(carNetWeight);
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
                        railSystem.getCar(opCarName).incGrossWeight(Integer.parseInt(weight));
                        validateTrain();
                        addWeightSucces = true;
                        System.out.println("Sukces");
                    } catch (TrainValidationException e) {
                        int carNetWeight = railSystem.getCar(opCarName).getNetWeight();
                        railSystem.getCar(opCarName).setGrossWeight(carNetWeight);
                        System.out.println("Błąd: " + e.getMessage());
                    }
                } else {
                    System.out.println("Masa ładunku może być maksymalnie 2 cyfrowa i nie może zawierać innyc znaków");
                }

            }
        }
    }

    void validateTrain() throws TrainValidationException {

        long carsNumber = railSystem.getTrain(opTrainKey).carsNames.size();

        List<Car> carsValues = new LinkedList<>(railSystem.getCarsValues());
        for (Car carValue : carsValues) {

        }


        List<Car> thisTrainElectricCars = new LinkedList<>();
        for (Car carValue : carsValues) {
            if (carValue.getTrainKey().equals(opTrainKey) && carValue.needElectricity)
                thisTrainElectricCars.add(carValue);
        }
        long electricCarsNumber = thisTrainElectricCars.size();

        int carsNetWeight = 0;
        for (Car carValue : carsValues) {
            if (carValue.getTrainKey().equals(opTrainKey)) {
                carsNetWeight += carValue.getNetWeight();
            }
        }
        int carsGrossWeight = 0;
        for (Car carValue : carsValues) {
            if (carValue.getTrainKey().equals(opTrainKey)) {
                carsGrossWeight += carValue.getGrossWeight();
            }
        }

        if (carsNetWeight > railSystem.getLocomotive(oplLocomotiveName).getMaxPullWeight()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono uciąg lokomotywy"
                    + "\nMaksymalny uciąg to: " + railSystem.getLocomotive(oplLocomotiveName).getMaxPullWeight()
                    + " ton a z tym wagonem było by: " + carsNetWeight + " ton"
            );
        } else if (carsGrossWeight > railSystem.getLocomotive(oplLocomotiveName).getMaxPullWeight()) {
            throw new TrainValidationException("Nie udało się załadować tego wagonu, przekroczono uciąg lokomotywy"
                    + "\nMaksymalny uciąg to: " + railSystem.getLocomotive(oplLocomotiveName).getMaxPullWeight()
                    + " ton a z tym ładunkiem było by: " + carsGrossWeight + " ton"
            );
        } else if (carsNumber > railSystem.getLocomotive(oplLocomotiveName).getMaxCarNumber()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono maksymalną ilość wagonów dla lokomotywy, czyli: "
                    + railSystem.getLocomotive(oplLocomotiveName).getMaxCarNumber());
        } else if (electricCarsNumber > railSystem.getLocomotive(oplLocomotiveName).getMaxElectricCarsNumber()) {
            throw new TrainValidationException("Nie udało się dodać tego wagonu, przekroczono maksymalną ilość wagonów wymagających " +
                    "podłączenia do sieci elektrycznej, czyli: " + railSystem.getLocomotive(oplLocomotiveName).getMaxElectricCarsNumber());
        } else System.out.println("Wagon pomyślnie dodany do składu pociągu");

    }

    //  Lokomotywa /////////////////////
    public void newLocomotive() {
        List<String> listedLocomotives = new LinkedList<>(railSystem.getLocomotivesKeys());

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
        railSystem.addLocomotive(name, opStationName);
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
                Map<String, Locomotive> allLocomotives = new LinkedHashMap<>(railSystem.getLocomotives());
                List<String> listedLocomotives = new LinkedList<>();
                listedLocomotives.clear();
                for (Map.Entry<String, Locomotive> entry : allLocomotives.entrySet()) {
                    if (entry.getValue().getHomeStation().equals(opStationName))
                        listedLocomotives.add(entry.getKey());
                }
                // wyswietlam listę lokomotyw
                if (listedLocomotives.size() == 0) {
                    System.out.println("Na tej stacji niema lokomotyw ");
                    chooseLocomotive();
                    for (Map.Entry<String, Locomotive> entry : allLocomotives.entrySet()) {
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

        railSystem.addCar(opCarName, opCarIsCargo, carType, opStationName);

    }

    public void newCarNameInterface() {
        List<String> listedCars = new LinkedList<>(railSystem.getCarsKeys());


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
        Map<String, Car> allCars = new LinkedHashMap<>(railSystem.getCars());

        boolean carDecisionSucces = false;
        boolean ifNoCarsSucces = false;

        System.out.println("Jeśli chcesz utworzyć nowy wagon to wpisz: nw ");
        System.out.println("Jeśli chcesz wybrac wagon z listy dostępnych na tej stacji to wpisz: lw ");

        while (!carDecisionSucces) {
            boolean isTrainCargo = railSystem.getTrain(opTrainKey).isCargo();

            String carDecision = "";
            carDecision = sc.nextLine();
            if (carDecision.equals("lw")) {
                //tworzę tymczasową listę wagonów na stacji
                List<String> listedCars = new LinkedList<>();
                for (Map.Entry<String, Car> entry : allCars.entrySet()) {
                    if (isTrainCargo && entry.getValue().getHomeStation().equals(opStationName) && entry.getValue().isCargo())
                        listedCars.add(entry.getKey());
                    if (!isTrainCargo && entry.getValue().getHomeStation().equals(opStationName) && !entry.getValue().isCargo())
                        listedCars.add(entry.getKey());
                }
                // wyswietlam listę wagonów
                if (listedCars.size() == 0) {
                    System.out.println("Na tej stacji niema nieprzydzielonych wagonów");
                    chooseCar();
                    for (Map.Entry<String, Car> entry : allCars.entrySet()) {
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
        Map<String, Line> allLines = new LinkedHashMap<>(railSystem.getLines());


        boolean stopAddingLines = false;
        System.out.println("Wybierz stację od której chcesz zacząć");
        chooseStation();

        opSourceStation = opStationName;
        System.out.println("Teraz wybierz pociąg dla którego chcesz zaplanować trasę");
        chooseTrain();
        oplLocomotiveName = railSystem.getTrain(opTrainKey).getLocomotiveName();

        // stacja początkowa lokomotywy - ustalamy
        railSystem.getLocomotive(oplLocomotiveName).setSourceStation(opStationName);
        System.out.println("Teraz powinniśmy po kolei zaplanować całą trasę dla naszego pociągu");

        while (!stopAddingLines) {
            List<String> listedLines = new LinkedList<>();
            for (Map.Entry<String, Line> entry : allLines.entrySet()) {
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
                            //   faktyczne dodanie trasy
                            railSystem.addPath(oplLocomotiveName, opSourceStation, opPathKey, opStationName, opPathLines, opTrainKey);
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
                            //   faktyczne dodanie trasy
                            railSystem.addPath(oplLocomotiveName, opSourceStation, opPathKey, opStationName, opPathLines, opTrainKey);
                            System.out.println("Pociąg: " + railSystem.getPath(opPathKey).trainsKeys);
                            System.out.println("Trasa: " + railSystem.getPath(opPathKey).getPathKey());
                            System.out.println("Linie :");
                            for (String line : railSystem.getPath(opPathKey).linesKeys) {
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
        Map<String, Line> allLines = new LinkedHashMap<>(railSystem.getLines());
        boolean lineDecisionSucces = false;
        //tworzę tymczasową listę linii ze stacji na której jesteśmy
        List<String> listedLines = new LinkedList<>();
        for (Map.Entry<String, Line> entry : allLines.entrySet()) {
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


    //  Linia (Line) /////////////////////
    public void newLine() {
        Map<String, Line> allLines = new LinkedHashMap<>(railSystem.getLines());
        opPreviousStationName = opStationName;
        String startStation = "";
        String endStation = "";
        List<String> listedLines = new LinkedList<>();
        for (Map.Entry<String, Line> entry : allLines.entrySet()) {
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

        System.out.println("Podaj teraz ile wynosi dystans w km pomiędzy tymi stacjami");
        System.out.println("podaj liczbę od 10 do 100 włącznie");
        boolean distanceSucces = false;
        String dec;
        int distance = 10;
        while (!distanceSucces) {
            dec = sc.nextLine();
            boolean isDigit = true;
            for (char c : dec.toCharArray()) {
                if (!Character.isDigit(c)) {
                    isDigit = false;
                }
            }
            if (isDigit) {
                if (Integer.parseInt(dec) > 9 && Integer.parseInt(dec) < 101) {
                    distance = Integer.parseInt(dec);
                    distanceSucces = true;
                    System.out.println("Dystans dodany");
                } else {
                    System.out.println("Błąd, liczba przekracza ustalony zakres");
                }
            } else {
                System.out.println("Bład, podaj poprawną wartość");
            }
        }

        // tworzenie nowej linii
        railSystem.addLine(newLineKey, startStation, endStation, distance);
        opStationName = endStation;
        opLineKey = newLineKey;
        System.out.println("Linia poprawnie utworzona");
    }

    //  Usuwanie (delate) /////////////////////

    public void remove() {
        System.out.println("UWAGA! Usuwająć 1 obiekt usuniesz też połączone z nim obiekty albo zmienisz ich stan,");
        System.out.println("np: ");
        System.out.println("usuwając stację usuwasz też i połączenia które wychodziły z tej stacji  ");
        System.out.println("usuwając pociąg usuwasz też lokomotywę i wagony razem z łądunkiem");
        System.out.println("usuwając lokomotywę usuwasz też cały pociąg razem z wagonami");
        System.out.println("usuwając wagon usuwasz też jego ładunek");
        System.out.println("usuwając trasę usuwasz też pociągi które kursuwały na tej trasie i wszytko co z nimi jest połączone");
        System.out.println("usuwając połączenie usuwasz też całą trasę która zawierała to połączenie");
        System.out.println();
        System.out.println("Wybierz jaki obiekt chcesz usunąć");
        System.out.println("Wpisz odpowiednią małą litere");
        System.out.println("s - (station) usuwanie stacji");
        System.out.println("t - (train) usuwanie pociągu");
        System.out.println("l - (locomotive) usuwanie lokomortywy");
        System.out.println("c - (car) usuwanie wagonu");
        System.out.println("p - (path) usuwanie trasy");
        System.out.println("j - (join) usuwanie połączenia między dwoma stacjami");
        boolean decision = false;
        String type = "";
        while (!decision) {
            String object = sc.nextLine();
            if (object.length() != 1)
                System.out.println("Można wpisać tylko 1 znak");
            else {
                if (object.equals("s")) {
                    chooseStationToRemove();
                    railSystem.removeStation_Line_Path_Train_Loco_Cars(opStationName);
                    System.out.println("Stacja: " + opStationName + " została usunięta");
                    decision = true;
                } else if (object.equals("t")) {
                    chooseTrainToRemove();
                    railSystem.removeTrain_Loco_Cars(opTrainKey);
                    System.out.println("Pociąg: " + opTrainKey + " został usunięty");
                    decision = true;
                } else if (object.equals("l")) {
                    chooseLocomotiveToRemove();
                    railSystem.removeLocomotive_Train_Cars(oplLocomotiveName);
                    System.out.println("Lokomotywa: " + oplLocomotiveName + " została usunięta");
                    decision = true;
                } else if (object.equals("c")) {
                    chooseCarToRemove();
                    railSystem.removeCar(opCarName);
                    System.out.println("Wagon: " + opCarName + " został usunięty");
                    decision = true;
                } else if (object.equals("p")) {
                    choosePathToRemove();
                    railSystem.removePath_Train_Loco_Cars(opPathKey);
                    System.out.println("Trasa: " + opPathKey + " została usunięta");
                    decision = true;
                } else if (object.equals("j")) {
                    chooseLineToRemove();
                    railSystem.removeLine_Path_Train_Loco_Cars(opLineKey);
                    System.out.println("Linia: " + opLineKey + " została usunięta");
                    decision = true;
                } else {
                    System.out.println("Błąd. można wpisywać wyłącznie literki z listy");
                }
            }
        }


    }

    public void chooseStationToRemove() {
        Map<String, Station> allStations = new LinkedHashMap<>(railSystem.getStations());
        //tworzę tymczasową listę stacji
        List<String> listedStations = new LinkedList<>();
        for (Map.Entry<String, Station> entry : allStations.entrySet()) {
            listedStations.add(entry.getKey());
        }
        // wyswietlam listę stacji
        for (String station : listedStations) {
            System.out.println(station);
        }
        System.out.println("Wpisz albo skopiuj nazwę stacji");
        String stationDecision = "";
        stationDecision = sc.nextLine();
        // walidacja nazwy stacji
        while (!listedStations.contains(stationDecision)) {
            stationDecision = sc.nextLine();
            if (!listedStations.contains(stationDecision)) {
                System.out.println("Wybrana przez ciebie stacja nie istnieje");
            }
        }
        // sukces:
        if (listedStations.contains(stationDecision)) {
            opStationName = stationDecision;
        }
    }

    public void chooseTrainToRemove() {
        Map<String, Train> allTrains = new LinkedHashMap<>(railSystem.getTrains());
        String trainDecision = "";
        //tworzę tymczasową listę pociągów
        List<String> listedTrains = new LinkedList<>();
        listedTrains.clear();
        for (Map.Entry<String, Train> entry : allTrains.entrySet()) {
            listedTrains.add(entry.getKey());
        }
        // wyswietlam listę pociągów
        System.out.println("Lista pociągów:");
        for (String train : listedTrains) {
            System.out.println(train);
        }
        System.out.println("Wpisz albo skopiuj nazwę pociągu który chcesz usunąć");
        // walidacja nazwy pociągu
        while (!listedTrains.contains(trainDecision)) {
            trainDecision = sc.nextLine();
            if (!listedTrains.contains(trainDecision)) {
                System.out.println("Wybrany przez ciebie pociąg nie istnieje");
            }
        }
        // sukces:
        if (listedTrains.contains(trainDecision)) {
            opTrainKey = trainDecision;
        }
    }

    public void chooseLocomotiveToRemove() {
        Map<String, Locomotive> allLocomotives = new LinkedHashMap<>(railSystem.getLocomotives());
        String locoDecision = "";
        //tworzę tymczasową listę lokomotyw
        List<String> listedLocomotives = new LinkedList<>();
        listedLocomotives.clear();
        for (Map.Entry<String, Locomotive> entry : allLocomotives.entrySet()) {
            listedLocomotives.add(entry.getKey());
        }
        // wyswietlam listę lokomotyw
        System.out.println("Lista lokomotyw:");
        for (String locomotive : listedLocomotives) {
            System.out.println(locomotive);
        }
        System.out.println("Wpisz albo skopiuj nazwę lokomotywy którą chcesz usunąć");
        // walidacja nazwy lokomotywy
        while (!listedLocomotives.contains(locoDecision)) {
            locoDecision = sc.nextLine();
            if (!listedLocomotives.contains(locoDecision)) {
                System.out.println("Wybrana przez ciebie lokomotywa nie istnieje");
            }
        }
        // sukces:
        if (listedLocomotives.contains(locoDecision)) {
            oplLocomotiveName = locoDecision;
        }
    }

    public void chooseCarToRemove() {
        Map<String, Car> allCars = new LinkedHashMap<>(railSystem.getCars());
        String carDecision = "";
        //tworzę tymczasową listę wagonów
        List<String> listedCars = new LinkedList<>();
        for (Map.Entry<String, Car> entry : allCars.entrySet()) {
            listedCars.add(entry.getKey());
        }
        // wyswietlam listę wagonów
        System.out.println("Lista wagonów:");
        for (String car : listedCars) {
            System.out.println(car);
        }
        System.out.println("Wpisz albo skopiuj nazwę wagonu który chcesz usunąć");
        // walidacja nazwy wagonu
        while (!listedCars.contains(carDecision)) {
            carDecision = sc.nextLine();
            if (!listedCars.contains(carDecision)) {
                System.out.println("Wybrany przez ciebie wagon nie istnieje");
            }
        }
        // sukces:
        if (listedCars.contains(carDecision)) {
            opCarName = carDecision;
        }
    }

    public void choosePathToRemove() {
        Map<String, Path> allPaths = new LinkedHashMap<>(railSystem.getPaths());
        String pathDecision = "";
        //tworzę tymczasową listę tras
        List<String> listedPaths = new LinkedList<>();
        for (Map.Entry<String, Path> entry : allPaths.entrySet()) {
            listedPaths.add(entry.getKey());
        }
        System.out.println("Lista tras:");
        for (String path : listedPaths) {
            System.out.println(path);
        }

        System.out.println("Wpisz albo skopiuj nazwę trasy którą chcesz usunąć");
        // walidacja nazwy pociągu
        while (!listedPaths.contains(pathDecision)) {
            pathDecision = sc.nextLine();
            if (!listedPaths.contains(pathDecision)) {
                System.out.println("Wybrana przez ciebie trasa nie istnieje");
            }
        }
        // sukces:
        if (listedPaths.contains(pathDecision)) {
            opPathKey = pathDecision;
        }
    }

    public void removeLine() {

    }

    public void chooseLineToRemove() {
        Map<String, Line> allLines = new LinkedHashMap<>(railSystem.getLines());
        String lineDecision = "";
        boolean lineDecisionSucces = false;
        //tworzę tymczasową listę linii ze stacji na której jesteśmy
        List<String> listedLines = new LinkedList<>();
        for (Map.Entry<String, Line> entry : allLines.entrySet()) {
            listedLines.add(entry.getKey());
        }
        System.out.println("Lista linii:");
        for (String line : listedLines) {
            System.out.println(line);
        }

        while (!listedLines.contains(lineDecision)) {
            lineDecision = sc.nextLine();
            if (!listedLines.contains(lineDecision)) {
                System.out.println("Wybrana przez ciebie linia nie istnieje");
            }
        }
        // sukces:
        if (listedLines.contains(lineDecision)) {
            opLineKey = lineDecision;
        }


    }


}




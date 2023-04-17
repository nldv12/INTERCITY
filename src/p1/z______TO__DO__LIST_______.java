package p1;

import p1.Cars.PassengerCar;

import java.util.LinkedList;
import java.util.Map;

public class z______TO__DO__LIST_______ {
    /*
    poprawiony task lokomotive speed change
    rail system mechanizmem Singleton k
    dodany railroad hazzard
    dodane usuwanie obiektów (do poprawy)



    1. Як зробити синхронізацію краще всього
    2. и це взвгвлі потрібно??? 27 лінійка CLI
    public Task_CLI(RailSystem railSystem) {
        this.railSystem = railSystem;
    }


    // nowy task pojizd jide po linii
    +++---Train porusza się po ustalonej trasie (wskazanej obiektem stacji startowej i docelowej).
            Trasę pomiędzy stacjami każdorazowo należy ustalić (nie musi być to trasa najkrótsza,
            ale każdorazowo musi być ustalona algorytmicznie na bazie grafu połączeń kolejowych).


    ---- Postój na stacjach pośrednich trwa 2 sekundy. Po dotarciu na stację docelową skład
            pociągu oczekuje 30 sekund, a następnie pociąg wyrusza w drogę powrotną
            (ponownie ustalając trasę) i kursuje cały czas w tym cyklu.
   system miliseconds


   do poprawy:
    ----- usuwanie stacji

   in Locomotive
   ---- public void calcAndSetDistancePassedPercantageLocal
        private int localLineDistance;
         private double distancePassedLocal = 0;
         private double distancePassedPercantageLocal = 0;

    ---- Kolejki na stacjach
            Należy zaimplementować również prewencję kolizji, w której pomiędzy dwoma stacjami może
            poruszać się maksymalnie jeden skład pociągu. Jeśli nastąpi taka sytuacja, że w trasie znajduje
            się już pociąg, inne oczekują w kolejce na zwolnienie trasy i w kolejności zgłoszenia oczekiwania
            będą przepuszczane przez pożądaną trakcję pomiędzy stacjami kolejowymi.
    ---- Nie można łączyć różnych funkcjonalności w jeden wątek.

    ++++ RailroadHazard Exception if train speed > 200km/h
            getMessage("Train nr: " + xxx + " przekroczył 200km/h jedzie od: " + xxx + " do "+ xxx)

    ++++ getReport(train) {
            train.key
            train.globalPositionPercentage (pomiędzy startową i docelową )
            train.cars (listed)
            train.whatLoaded
   -------  train.next station
            train.localPositionPercentage (pomiędzy najbliższymi stacjami )

        dostępne na konsoli (w menu po wyborze odpowiedniej opcji)
        }

     ---- AppSate.txt => co 5 sekund wszystkie Trains.getReport(train)
          - Cars sorted rosnąco według WAGI
          - Trains sorted malejąco względem globalPositionPercentage left

     ++++ 100 Stations
     ++++ 25 Trains every 5-10 Cars
mmjmj
     ---- Presentation.java
            każda funkcjonalność (strategiczna) dostępna do zaprezentowanai w prosty sposób (czytelny)


--------- TO USE:::
        ++++ Dziedziczenie
        ++++ Kolekcje
        ++++ Klasy abstrakcyjne
        ++++ Typy Generyczne
        ---- Interfejsy
        ---- Lambda wyrażenia





    - wymyślić po 2 dodatkowe pola dla każdej klasy
    - wymyślić po 2 dodatkowe metody dla każdej klasy odpowiadające jej tematyce
    - można by zrobić tak żeby podczas tworzenia pociągu można było zaplanować dla niego trasę
    - można by zrobić wyświetlenie tras, linii, pociągów itp.. jako odzielne menu






    */



//    public void defaultRailSystemFill() {
//
//        int stationsSize = railSystem.stations.size();
//        int pTrainNumber = 1;
//        int cTrainNumber = 1;
//        int locoNumber = 1;
//        int carNumber = 1;
//
//        for (Map.Entry<String, Station> stationEntry : railSystem.stations.entrySet()) {
//            // tworzę 2 pociągi na każdej stacji 1 osobowy i 1 ciężarowy
//            for (int i = 0; i < 2; i++) {
//                // lista wszystkich linii
//                LinkedList<String> allLines = new LinkedList<>();
//                for (Map.Entry<String, Line> line : railSystem.lines.entrySet()) {
//                    allLines.add(line.getKey());
//                }
//                // projektowanie Path dla pociągu (taka sama dla pasażerskiego i ciężarowego)
//                LinkedList<String> thisPath = new LinkedList<>();
//                String startStration = stationEntry.getKey();
//                String prevStration = stationEntry.getKey();
//                for (int j = 0; j <5; j++) {
//                    for (String line : allLines) {
//                        if (line.startsWith(startStration) && !line.endsWith(prevStration)){
//                            thisPath.add(line);
//                            String[] cities = line.split("_");
//                            startStration = cities[1];
//                            prevStration = cities[0];
//                            break;
//                        }
//                    }
//                }
//
//                String pathKey = stationEntry.getKey() + "__" + startStration;
//                railSystem.paths.put(pathKey, new Path(pathKey, thisPath));
//
//
//                // Passenger train
//                String ptTrainName = "PT_000" + (pTrainNumber++);
//                railSystem.trains.put(ptTrainName, new Train(ptTrainName));
//                railSystem.trains.get(ptTrainName).setHomeStationName(stationEntry.getKey());
//                String ptLocoName = "l" + (locoNumber++);
//                railSystem.locomotives.put(ptLocoName, new Locomotive(ptLocoName, stationEntry.getKey()));
//                railSystem.trains.get(ptTrainName).setlocomotiveName(ptLocoName);
//                // do każdego pociągu 1 trasa
//                railSystem.locomotives.get(ptLocoName).setSourceStation(stationEntry.getKey());
//                railSystem.locomotives.get(ptLocoName).setDestinationStation(startStration);
//                railSystem.locomotives.get(ptLocoName).setPathKey(pathKey);
//                railSystem.paths.get(pathKey).trainsKeys.add(ptTrainName);
//                // do każdego pociągu 5 wagonów
//                for (int j = 0; j < 5; j++) {
//                    String pCarName = "c" + (carNumber++);
//                    railSystem.cars.put(pCarName, new PassengerCar(stationEntry.getKey()));
//                    railSystem.cars.get(pCarName).setHomeStation(stationEntry.getKey());
//                    railSystem.cars.get(pCarName).setTrainKey(ptTrainName);
//                    railSystem.trains.get(ptTrainName).carsNames.add(pCarName);
//
//                }
//                // Cargo train
//                String ctTrainName = "CT_000" + (cTrainNumber++);
//                railSystem.trains.put(ctTrainName, new Train(ctTrainName));
//                railSystem.trains.get(ctTrainName).setHomeStationName(stationEntry.getKey());
//                String ctLocoName = "l" + (locoNumber++);
//                railSystem.locomotives.put(ctLocoName, new Locomotive(ctLocoName, stationEntry.getKey()));
//                railSystem.trains.get(ctTrainName).setlocomotiveName(ctLocoName);
//                // do każdego pociągu 1 trasa
//                railSystem.locomotives.get(ctLocoName).setSourceStation(stationEntry.getKey());
//                railSystem.locomotives.get(ctLocoName).setDestinationStation(startStration);
//                railSystem.locomotives.get(ctLocoName).setPathKey(pathKey);
//                railSystem.paths.get(pathKey).trainsKeys.add(ctTrainName);
//                // do każdego pociągu 5 wagonów
//                for (int j = 0; j < 5; j++) {
//                    String cCarName = "c" + (carNumber++);
//                    railSystem.cars.put(cCarName, new PassengerCar(stationEntry.getKey()));
//                    railSystem.cars.get(cCarName).setHomeStation(stationEntry.getKey());
//                    railSystem.cars.get(cCarName).setTrainKey(ctTrainName);
//                    railSystem.trains.get(ctTrainName).carsNames.add(cCarName);
//
//                }
//
//            }
//        }
//    }



}

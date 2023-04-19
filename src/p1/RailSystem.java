package p1;

import p1.Cars.*;
import p1.Cars.CargoCars.*;
import p1.Enums.Locations;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RailSystem {

    private static RailSystem railSystem = new RailSystem();

    public static RailSystem getRailSystem() {
        return railSystem;
    }

    private RailSystem() {
        addDefaultLocations();
        stationsAndLinks();

    }

//    public Queue<String> alerts = new LinkedList<>();
//    Map<String, Station> stations = new LinkedHashMap<>();
//    Map<String, Locomotive> locomotives = new LinkedHashMap<>();
//    Map<String, Train> trains = new LinkedHashMap<>();
//    Map<String, Car> cars = new LinkedHashMap<>();
//    Map<String, Line> lines = new LinkedHashMap<>();
//    Map<String, Path> paths = new LinkedHashMap<>();

    public Queue<String> alerts = new LinkedList<>(); //???? też ConcurrentHashMap?
    ConcurrentHashMap<String, Station> stations = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Locomotive> locomotives = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Car> cars = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Line> lines = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Path> paths = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Train> trains = new ConcurrentHashMap<>();

    // GETTERS

    public synchronized Map<String, Station> getStations() {
        return stations;
    }

    public synchronized Map<String, Locomotive> getLocomotives() {
        return locomotives;
    }

    public synchronized Map<String, Train> getTrains() {
        return trains;
    }

    public synchronized Map<String, Car> getCars() {
        return cars;
    }

    public synchronized Map<String, Line> getLines() {
        return lines;
    }

    public synchronized Map<String, Path> getPaths() {
        return paths;
    }

    //lists of keys and values from map

    public synchronized List getStrationsKeys() {
        return new LinkedList<>(stations.keySet());
    }

    public synchronized List getLocomotivesKeys() {
        return new LinkedList<>(locomotives.keySet());
    }

    public synchronized List getLocomotivesValues() {
        return new LinkedList<>(locomotives.values());
    }

    public synchronized List getTrainsKeys() {
        return new LinkedList<>(trains.keySet());
    }

    public synchronized List getTrainsValues() {
        return new LinkedList<>(trains.values());
    }

    public synchronized List getCarsKeys() {
        return new LinkedList<>(cars.keySet());
    }

    public synchronized List getCarsValues() {
        return new LinkedList<>(cars.values());
    }

    public synchronized List getLinesKeys() {
        return new LinkedList<>(lines.keySet());
    }

    public synchronized List getLinesValues() {
        return new LinkedList<>(lines.values());
    }

    public synchronized List getPathsKeys() {
        return new LinkedList<>(paths.keySet());
    }

    public synchronized List getPathsValues() {
        return new LinkedList<>(paths.values());
    }


    public synchronized Line getLineByKey(String key) {
        return this.lines.get(key);
    }

    public synchronized Path getPathByKey(String key) {
        return this.paths.get(key);
    }

    public synchronized List getPathLinesByPathKey(String key) {
        return this.paths.get(key).linesKeys;
    }

    public synchronized boolean isLineEmpty(String lineKey) {
        boolean empty = false;
        for (Locomotive locomotive : this.locomotives.values()) {
            if (locomotive.getCurrentLineKey().equals(lineKey)) {
                empty = false;
                break;
            } else
                empty = true;
        }

        return empty;
    }

    public synchronized LinkedList getCarsKeysSortedByCarWeight(String trainKey) {
        LinkedList<Car> cars = new LinkedList<>();
        for (Car car : this.cars.values()) {
            if (car.getTrainKey().equals(trainKey)) {
                cars.add(car);
            }
        }
        cars.sort(new CarComparator());
        LinkedList<String> carsNames = new LinkedList<>();
        for (Car car : cars) {
            carsNames.add(car.getName());
        }

        return carsNames;
    }


    // hooks


    public synchronized Locomotive getLocomotive(String locomotiveName) {
        return this.locomotives.get(locomotiveName);
    }

    public synchronized Train getTrain(String trainKey) {
        return this.trains.get(trainKey);
    }

    public synchronized Car getCar(String carName) {
        return this.cars.get(carName);
    }

    public synchronized Path getPath(String pathKey) {
        return this.paths.get(pathKey);
    }


    public synchronized String getLocoNameByTrain(String trainKey) {
        return this.trains.get(trainKey).getLocomotiveName();
    }

    public synchronized int getCarsCountByTrain(String trainKey) {
        return this.trains.get(trainKey).carsNames.size();
    }

    // KEY METHODS
    void stationsAndLinks() {
        // tworzę wszystkie domyślne stacje

        // tworze listę gdzie 1 pozycja to klucz i wartość z mapy

        List<Map.Entry<String, Station>> stationEntries = new ArrayList<>(stations.entrySet());
        stationEntries.sort(Comparator.comparing(Map.Entry::getKey)); //sortowanie po nazwie

        Random random = new Random();

        Set<String> createdConnections = new LinkedHashSet<>(); // set do przechowywania już utworzonych połączeń


        // wykonanie tego kodu spowoduje utworzenie większej liczby połączeń od każdej stacji
        for (int n = 0; n < 1; n++) {
            // Tworzenie połączenia od każdej stacji
            for (int i = 0; i < stationEntries.size(); i++) {
                String key1 = stationEntries.get(i).getKey();
                Station station1 = stationEntries.get(i).getValue();
                String key2 = "";
                Station station2;

                // Szukanie innej stacji, która jeszcze nie ma połączenia z aktualną stacją
                do {
                    int i2 = random.nextInt(stations.size() - 1) + 1;
                    if (i2 != i) {
                        key2 = stationEntries.get(i2).getKey();
                        station2 = stationEntries.get(i2).getValue();
                    }
                } while (createdConnections.contains(key1 + "_" + key2) || key2.equals("")); // Sprawdzanie, czy połączenie już istnieje

                // Tworzenie zapisu w mapie z połączeniem
                String key = key1 + "_" + key2;
                String revertedKey = key2 + "_" + key1;
                Line line = new Line(key1, key2);
                Line revertedLine = new Line(key2, key1);

                lines.put(key, line);
                lines.put(revertedKey, revertedLine);
                int distance = random.nextInt(91) + 10;
                this.lines.get(key).setDistance(distance);
                this.lines.get(revertedKey).setDistance(distance);
                createdConnections.add(key); // Dodanie połączenia do zbioru już utworzonych połączeń
                createdConnections.add(revertedKey); // Dodanie połączenia do zbioru już utworzonych połączeń
            }
        }


        /// sprawdzenie jak to działa
        List<String> myStations = new LinkedList<>();
        LinkedHashSet<String> myHashSet = new LinkedHashSet<>(); // set do przechowywania już utworzonych połączeń

        for (Map.Entry<String, Station> entry : this.stations.entrySet()) {
            myStations.add(entry.getKey());
            for (String line : createdConnections) {
                if (line.startsWith(entry.getKey())) {
                    myHashSet.add(entry.getKey());
                }

            }
        }

//        for (String line : createdConnections) {
//            System.out.println(line);
//        }
//        System.out.println("Liczba lini w hash secie połączeń : " + createdConnections.size());
//        System.out.println("Liczba stacji : " + myStations.size());
//        System.out.println("Liczba stacji które mają min 1 linie  : " + myHashSet.size());


    }

    void showStations() {
        System.out.println("Map of stations");
        System.out.println("Number of stations: " + stations.size());
        for (Map.Entry<String, Station> entry : stations.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().name);
        }

    }

    void showLines() {
        System.out.println("Map of lines");
        System.out.println("Number of lines: " + lines.size());
        for (Map.Entry<String, Line> entry : lines.entrySet()) {
            System.out.println(entry.getKey());
//            System.out.println(entry.getKey() + " - " + entry.getValue());
//            System.out.println(entry.getKey() + " - " + entry.getValue().station1Key+ " " + entry.getValue().station2Key);
        }

    }

    void moveLocomotives() {
//        List<String> listedStations = new LinkedList<>();
        for (Map.Entry<String, Locomotive> entry : this.locomotives.entrySet()) {
            entry.getValue().setMoving(true);
        }


    }


    //    void filrailsTest (){
//        this.locomotives.put("l1",new Locomotive("l1","LONDON"));
//        this.lines.put("LONDON_BIRMINGHAM", new Line("LONDON","BIRMINGHAM"));
//        this.lines.get("LONDON_BIRMINGHAM").setDistance(20);
//
//        this.lines.put("BIRMINGHAM_LONDON", new Line("BIRMINGHAM","LONDON"));
//        this.lines.get("BIRMINGHAM_LONDON").setDistance(20);
//
//        this.lines.put("BIRMINGHAM_LEEDS", new Line("BIRMINGHAM","LEEDS"));
//        this.lines.get("BIRMINGHAM_LEEDS").setDistance(20);
//
//        this.lines.put("LEEDS_BIRMINGHAM", new Line("LEEDS","BIRMINGHAM"));
//        this.lines.get("LEEDS_BIRMINGHAM").setDistance(20);
//
//        this.lines.put("LEEDS_MANCHESTER", new Line("LEEDS","MANCHESTER"));
//        this.lines.get("LEEDS_MANCHESTER").setDistance(20);
//
//        this.lines.put("MANCHESTER_LEEDS", new Line("MANCHESTER","LEEDS"));
//        this.lines.get("MANCHESTER_LEEDS").setDistance(20);
//
//        this.lines.put("MANCHESTER_LIVERPOOL", new Line("MANCHESTER","LIVERPOOL"));
//        this.lines.get("MANCHESTER_LIVERPOOL").setDistance(20);
//
//        this.lines.put("LIVERPOOL_MANCHESTER", new Line("LIVERPOOL","MANCHESTER"));
//        this.lines.get("LIVERPOOL_MANCHESTER").setDistance(20);
//
//        this.locomotives.get("l1").linesKeys.add("LONDON_BIRMINGHAM");
//        this.locomotives.get("l1").linesKeys.add("BIRMINGHAM_LEEDS");
//        this.locomotives.get("l1").linesKeys.add("LEEDS_MANCHESTER");
//        this.locomotives.get("l1").linesKeys.add("MANCHESTER_LIVERPOOL");
//        LinkedList<String> lines = new LinkedList<>();
//        lines.add("LONDON_BIRMINGHAM");
//        lines.add("BIRMINGHAM_LEEDS");
//        lines.add("LEEDS_MANCHESTER");
//        lines.add("MANCHESTER_LIVERPOOL");
//
//        this.paths.put("LONDON_LIVERPOOL", new Path("LONDON_LIVERPOOL", lines));
//        this.locomotives.get("l1").setPathKey("LONDON_LIVERPOOL");
//        this.locomotives.get("l1").setCurrentStation(Optional.of("LONDON"));
//        this.locomotives.get("l1").setSourceStation("LONDON");
//        this.locomotives.get("l1").setDestinationStation("LIVERPOOL");
//
//
//
//    }
    void addDefaultLocations() {
        String[] locationsArr = {
                "LONDON",
                "MANCHESTER",
                "BIRMINGHAM",
                "LIVERPOOL",
                "NEWCASTLE",
                "LEEDS",
                "BRISTOL",
                "SHEFFIELD",
                "NOTTINGHAM",
                "CARDIFF",
                "SOUTHAMPTON",
                "BRIGHTON",
                "EDINBURGH",
                "GLASGOW",
                "ABERDEEN",
                "BELFAST",
                "DUBLIN",
                "CAMBRIDGE",
                "OXFORD",
                "YORK",
                "CANTERBURY",
                "WINCHESTER",
                "SALISBURY",
                "BATH",
                "EXETER",
                "PLYMOUTH",
                "PORTSMOUTH",
                "READING",
                "LEICESTER",
                "COVENTRY",
                "STOKE",
                "WOLVERHAMPTON",
                "DERBY",
                "BLACKPOOL",
                "IPSWICH",
                "NORWICH",
                "BOURNEMOUTH",
                "SOUTHEND",
                "MILTON",
                "NORTHAMPTON",
                "PETERBOROUGH",
                "LINCOLN",
                "CHESTER",
                "LANCASTER",
                "WAKEFIELD",
                "ROTHERHAM",
                "DONCASTER",
                "BARNSLEY",
                "STOCKTON",
                "SUNDERLAND",
                "WIGAN",
                "OLDHAM",
                "ROCHDALE",
                "BOLTON",
                "WARRINGTON",
                "STHELENS",
                "HALIFAX",
                "HUDDERSFIELD",
                "BLACKBURN",
                "BURNLEY",
                "PRESTON",
                "CHORLEY",
                "WIDNES",
                "LYTHAM",
                "FLEETWOOD",
                "POULTON",
                "GARSTANG",
                "LONGRIDGE",
                "ORMSKIRK",
                "SKELMERSDALE",
                "LANCASHIRE",
                "RIBBLE",
                "CLITHEROE",
                "PREESALL",
                "KIRKHAM",
                "THORNTON",
                "BISPHAM",
                "KIRKBY",
                "ULVERSTON",
                "KENDAL",
                "GRANGE",
                "MILNTHORPE",
                "KESWICK",
                "COCKERMOUTH",
                "WORKINGTON",
                "WHITEHAVEN",
                "CUMBRIA",
                "HEXHAM",
                "ALNWICK",
                "MORPETH",
                "BLYTH",
                "BEDLINGTON",
                "NEWBIGGIN",
                "CRESSWELL",
                "AMBLE",
                "NORTHUMBERLAND",
                "ASHINGTON",
                "SEAHAM",
                "HARTLEPOOL",
                "MIDDLESBROUGH",
                "REDCAR",
                "GUISBOROUGH",
                "HARROGATE",
                "DARLINGTON",
                "HEBDEN",
                "SOWERBY",
                "HOLMFIRTH",
                "TODMORDEN",
                "RIPPONDEN",
                "BRIGHOUSE"
        };
        for (int i = 0; i < locationsArr.length; i++) {
            String key = locationsArr[i];
            Station station = new Station(key);
            stations.put(key, station);

        }
    }

    public void defaultRailSystemFill() {
        Random random = new Random();


        for (Map.Entry<String, Station> stationEntry : this.stations.entrySet()) {

            // lista wszystkich linii
            LinkedList<String> allLines = new LinkedList<>();
            for (Map.Entry<String, Line> line : this.lines.entrySet()) {
                allLines.add(line.getKey());
            }
            // projektowanie Path
            LinkedList<String> thisPath = new LinkedList<>();
            String startStration = stationEntry.getKey();
            String prevStration = stationEntry.getKey();
            for (int j = 0; j < 7; j++) {
                for (String line : allLines) {
                    if (line.startsWith(startStration) && !line.endsWith(prevStration)) {
                        thisPath.add(line);
                        String[] cities = line.split("_");
                        startStration = cities[1];
                        prevStration = cities[0];
                        break;
                    }
                }
            }
            String pathKey = stationEntry.getKey() + "__" + startStration;
            int distance = 0;
            for (Map.Entry<String, Line> line : this.lines.entrySet()) {
                if (thisPath.contains(line.getKey())) {
                    distance += line.getValue().getDistance();
                }
            }
            this.paths.put(pathKey, new Path(pathKey, thisPath, distance));
        }

        int pTrainNumber = 1;
        int cTrainNumber = 1;
        int locoNumber = 1;
        int carNumber = 1;


        LinkedList<String> first13stations = new LinkedList<>();
        int countStation = 0;
        for (Station station : this.stations.values()) {
            first13stations.add(station.name);
            countStation++;
            if (countStation == 13) {
                break;
            }
        }

        LinkedList<String> first13paths = new LinkedList<>();
        int countPath = 0;
        for (Path path : this.paths.values()) {
            first13paths.add(path.getPathKey());
            countPath++;
            if (countPath == 13) {
                break;
            }
        }

        for (int i = 0; i < 13; i++) {
            String[] pathKey = first13paths.get(i).split("__");
            // Passenger train
            String ptTrainName = "PT_000" + (pTrainNumber++);
            this.addTrain(ptTrainName, first13stations.get(i));
            String ptLocoName = "l" + (locoNumber++);
            this.locomotives.put(ptLocoName, new Locomotive(ptLocoName, first13stations.get(i)));
            this.trains.get(ptTrainName).setlocomotiveName(ptLocoName);
            this.locomotives.get(ptLocoName).setTrainKey(ptTrainName);
            // do każdego pociągu 1 trasa
            this.locomotives.get(ptLocoName).setSourceStation(first13stations.get(i));
            this.locomotives.get(ptLocoName).setDestinationStation(pathKey[1]);
            this.locomotives.get(ptLocoName).setPathKey(first13paths.get(i));
            this.locomotives.get(ptLocoName).setCurrentStation(Optional.of(first13stations.get(i)));
            this.paths.get(first13paths.get(i)).trainsKeys.add(ptTrainName);
            // do każdego pociągu 5 wagonówghg

            int carsNumber = random.nextInt(10 - 5 + 1) + 5;
            for (int j = 0; j < carsNumber; j++) {
                String pCarName = "c" + (carNumber++);
                this.cars.put(pCarName, new LuggagePostCar(pCarName, first13stations.get(i)));
                int weight = random.nextInt(100 - 40 + 1) + 40;
                this.cars.get(pCarName).setGrossWeight(weight);
                this.cars.get(pCarName).setHomeStation(first13stations.get(i));
                this.cars.get(pCarName).setTrainKey(ptTrainName);
                this.trains.get(ptTrainName).carsNames.add(pCarName);
            }
            // Cargo train
            String ctTrainName = "CT_000" + (cTrainNumber++);
            this.addTrain(ctTrainName, first13stations.get(i));
            String ctLocoName = "l" + (locoNumber++);
            this.locomotives.put(ctLocoName, new Locomotive(ctLocoName, first13stations.get(i)));
            this.trains.get(ctTrainName).setlocomotiveName(ctLocoName);
            this.locomotives.get(ctLocoName).setTrainKey(ctTrainName);
            // do każdego pociągu 1 trasa
            this.locomotives.get(ctLocoName).setSourceStation(first13stations.get(i));
            this.locomotives.get(ctLocoName).setDestinationStation(pathKey[1]);
            this.locomotives.get(ctLocoName).setPathKey(first13paths.get(i));
            this.locomotives.get(ctLocoName).setCurrentStation(Optional.of(first13stations.get(i)));
            this.paths.get(first13paths.get(i)).trainsKeys.add(ctTrainName);
            carsNumber = random.nextInt(10 - 5 + 1) + 5;
            for (int j = 0; j < carsNumber; j++) {
                String cCarName = "c" + (carNumber++);
                this.cars.put(cCarName, new BasicCargoCar(cCarName, first13stations.get(i)));
                int weight = random.nextInt(100 - 40 + 1) + 40;
                this.cars.get(cCarName).setGrossWeight(weight);
                this.cars.get(cCarName).setHomeStation(first13stations.get(i));
                this.cars.get(cCarName).setTrainKey(ctTrainName);
                this.trains.get(ctTrainName).carsNames.add(cCarName);

            }
        }


    }


    // SETTERS
    public synchronized void addStation(String key) {
        this.stations.put(key, new Station(key));
    }

    public synchronized void addTrain(String key, String homeStationName) {
        this.trains.put(key, new Train(key, homeStationName));
    }

    public synchronized void addLocomotive(String name, String homeStationName) {
        this.locomotives.put(name, new Locomotive(name, homeStationName));
    }

    public synchronized void addCar(String opCarName, boolean opCarIsCargo, String carType, String opStationName) {
        if (opCarIsCargo) {
            switch (carType) {
                case "1" -> this.cars.put(opCarName, new BasicCargoCar(opCarName, opStationName));
                case "2" -> this.cars.put(opCarName, new ExplosiveMaterialCargoCar(opCarName, opStationName));
                case "3" -> this.cars.put(opCarName, new GasMaterialCargoCar(opCarName, opStationName));
                case "4" -> this.cars.put(opCarName, new HeavyCargoCar(opCarName, opStationName));
                case "5" -> this.cars.put(opCarName, new LiquidMaterialCargoCar(opCarName, opStationName));
                case "6" -> this.cars.put(opCarName, new LiquidToxicMaterialCargoCar(opCarName, opStationName));
                case "7" -> this.cars.put(opCarName, new RefrigeratedCargoCar(opCarName, opStationName));
                case "8" -> this.cars.put(opCarName, new ToxicMaterialCargoCar(opCarName, opStationName));
                default -> System.out.println("Err");
            }
        } else {
            switch (carType) {
                case "1" -> this.cars.put(opCarName, new LuggagePostCar(opCarName, opStationName));
                case "2" -> this.cars.put(opCarName, new PassengerCar(opCarName, opStationName));
                case "3" -> this.cars.put(opCarName, new PostCar(opCarName, opStationName));
                case "4" -> this.cars.put(opCarName, new RestaurantCar(opCarName, opStationName));
                default -> System.out.println("Err");
            }
        }
    }

    public synchronized void addPath(String oplLocomotiveName, String opSourceStation, String opPathKey, String opStationName,
                                     LinkedList<String> opPathLines, String opTrainKey) {
        // stacja końcowa lokomotywy - ustalamy
        this.locomotives.get(oplLocomotiveName).setDestinationStation(opStationName);
        String pathKey = opSourceStation + "__" + opStationName;
        this.locomotives.get(oplLocomotiveName).setPathKey(pathKey);
        opPathKey = pathKey;
        // tworzymy obiekt path
        int distance = 0;
        for (Map.Entry<String, Line> line : this.lines.entrySet()) {
            if (opPathLines.contains(line.getKey())) {
                distance += line.getValue().getDistance();
            }
        }
        this.paths.put(pathKey, new Path(pathKey, opPathLines, distance));
        this.paths.get(pathKey).trainsKeys.add(opTrainKey);
    }

    public synchronized void addLine(String newLineKey, String startStation, String endStation, int distance) {
        this.lines.put(newLineKey, new Line(startStation, endStation));
        this.lines.get(newLineKey).setDistance(distance);
    }


    // REMOVE
    public synchronized void removeStation(String opStationName) {
        this.stations.remove(opStationName);
    }

    public synchronized void removeTrain(String opTrainKey) {
        this.trains.remove(opTrainKey);
    }

    public synchronized void removeLocomotive(String oplLocomotiveName) {
        this.locomotives.remove(oplLocomotiveName);
    }

    public synchronized void removeCar(String opCarName) {
        String trainKey = this.getCar(opCarName).getTrainKey();
        this.getTrain(trainKey).carsNames.remove(opCarName);
        this.cars.remove(opCarName);
    }

    public synchronized void removePath(String opPathKey) {
        this.paths.remove(opPathKey);
    }

    public synchronized void removeLine(String opLineKey) {
        this.lines.remove(opLineKey);
    }

    public synchronized void removeTrain_Loco_Cars(String opTrainKey) {
        String locomotiveName = this.getTrain(opTrainKey).getLocomotiveName();
        removeLocomotive(locomotiveName);
        removeTrain(opTrainKey);
        removeCarsOfTrain(opTrainKey);
    }

    public synchronized void removeCarsOfTrain(String trainKey) {
        Map<String, Car> copyOfCars = new HashMap<>(this.getCars());
        for (Map.Entry<String, Car> carEntry : copyOfCars.entrySet()) {
            if (carEntry.getValue().getTrainKey().equals(trainKey)) {
                this.cars.remove(carEntry.getKey());
            }
        }
    }

    public synchronized void removeLocomotive_Train_Cars(String oplLocomotiveName) {
        String trainKey = this.getLocomotive(oplLocomotiveName).getTrainKey();
        removeLocomotive(oplLocomotiveName);
        removeTrain(trainKey);
        removeCarsOfTrain(trainKey);
    }

    public synchronized void removePath_Train_Loco_Cars(String opPathKey) {
        List<String> trainsKeys = new LinkedList<>(this.paths.get(opPathKey).trainsKeys);
        List<String> locomotiveNames = new LinkedList<>();
        for (String trainsKey : trainsKeys) {
            locomotiveNames.add(getLocoNameByTrain(trainsKey));
            removeTrain(trainsKey);
            removeCarsOfTrain(trainsKey);
        }
        for (String locomotiveName : locomotiveNames) {
            removeLocomotive(locomotiveName);
        }
        removePath(opPathKey);

    }

    public synchronized void removeLine_Path_Train_Loco_Cars(String opLineKey) {
        List<String> pathsKeysToRemove = new LinkedList<>();

        for (Map.Entry<String, Path> pathEntry : this.paths.entrySet()){
            if (pathEntry.getValue().linesKeys.contains(opLineKey)){
                pathsKeysToRemove.add(pathEntry.getKey());
            }
        }
        for (String pathKey : pathsKeysToRemove) {
            removePath_Train_Loco_Cars(pathKey);
            removePath(pathKey);
        }
        removeLine(opLineKey);
    }

    public synchronized void removeStation_Line_Path_Train_Loco_Cars(String opStationName){
        List<String> linesToRemove = new LinkedList<>();
        for (Map.Entry<String, Line> lineEntry : this.lines.entrySet()) {
            if ((lineEntry.getKey().startsWith(opStationName) || lineEntry.getKey().endsWith(opStationName))) {
                linesToRemove.add(lineEntry.getKey());
            }
        }
        for (String line : linesToRemove) {
            removeLine_Path_Train_Loco_Cars(line);
        }

        removeStation(opStationName);

    }


    /// przetestuj usuwanie wszytkiego :)


}




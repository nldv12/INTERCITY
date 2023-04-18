package p1;

import p1.Cars.Car;
import p1.Cars.CargoCars.BasicCargoCar;
import p1.Cars.LuggagePostCar;
import p1.Cars.PassengerCar;
import p1.Cars.PostCar;
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

    public Queue<String> alerts = new LinkedList<>();
    Map<String, Station> stations = new LinkedHashMap<>();
    Map<String, Locomotive> locomotives = new LinkedHashMap<>();
    Map<String, Car> cars = new LinkedHashMap<>();
    Map<String, Line> lines = new LinkedHashMap<>();
    Map<String, Path> paths = new LinkedHashMap<>();
    Map<String, Train> trains = new LinkedHashMap<>();

//    public Queue<String> alerts = new LinkedList<>(); //???? też ConcurrentHashMap?
//    ConcurrentHashMap<String, Station> stations = new ConcurrentHashMap<>();
//    ConcurrentHashMap<String, Locomotive> locomotives = new ConcurrentHashMap<>();
//    ConcurrentHashMap<String, Car> cars = new ConcurrentHashMap<>();
//    ConcurrentHashMap<String, Line> lines = new ConcurrentHashMap<>();
//    ConcurrentHashMap<String, Path> paths = new ConcurrentHashMap<>();
//    ConcurrentHashMap<String, Train> trains = new ConcurrentHashMap<>();


    public Line getLineByKey(String key) {
        return this.lines.get(key);
    }

    public Path getPathByKey(String key) {
        return this.paths.get(key);
    }

    public List getPathLinesByPathKey(String key) {
        return this.paths.get(key).linesKeys;
    }

    public boolean isLineEmpty(String lineKey) {
        boolean empty = false;
        for (Locomotive locomotive : railSystem.locomotives.values()) {
            if (locomotive.getCurrentLineKey().equals(lineKey)) {
                empty = false;
                break;
            } else
                empty = true;
        }

        return empty;
    }

    public LinkedList getCarsKeysSortedByCarWeight(String trainKey) {
        LinkedList<Car> cars = new LinkedList<>();
        for (Car car : railSystem.cars.values()) {
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
//        railSystem.locomotives.put("l1",new Locomotive("l1","LONDON"));
//        railSystem.lines.put("LONDON_BIRMINGHAM", new Line("LONDON","BIRMINGHAM"));
//        railSystem.lines.get("LONDON_BIRMINGHAM").setDistance(20);
//
//        railSystem.lines.put("BIRMINGHAM_LONDON", new Line("BIRMINGHAM","LONDON"));
//        railSystem.lines.get("BIRMINGHAM_LONDON").setDistance(20);
//
//        railSystem.lines.put("BIRMINGHAM_LEEDS", new Line("BIRMINGHAM","LEEDS"));
//        railSystem.lines.get("BIRMINGHAM_LEEDS").setDistance(20);
//
//        railSystem.lines.put("LEEDS_BIRMINGHAM", new Line("LEEDS","BIRMINGHAM"));
//        railSystem.lines.get("LEEDS_BIRMINGHAM").setDistance(20);
//
//        railSystem.lines.put("LEEDS_MANCHESTER", new Line("LEEDS","MANCHESTER"));
//        railSystem.lines.get("LEEDS_MANCHESTER").setDistance(20);
//
//        railSystem.lines.put("MANCHESTER_LEEDS", new Line("MANCHESTER","LEEDS"));
//        railSystem.lines.get("MANCHESTER_LEEDS").setDistance(20);
//
//        railSystem.lines.put("MANCHESTER_LIVERPOOL", new Line("MANCHESTER","LIVERPOOL"));
//        railSystem.lines.get("MANCHESTER_LIVERPOOL").setDistance(20);
//
//        railSystem.lines.put("LIVERPOOL_MANCHESTER", new Line("LIVERPOOL","MANCHESTER"));
//        railSystem.lines.get("LIVERPOOL_MANCHESTER").setDistance(20);
//
//        railSystem.locomotives.get("l1").linesKeys.add("LONDON_BIRMINGHAM");
//        railSystem.locomotives.get("l1").linesKeys.add("BIRMINGHAM_LEEDS");
//        railSystem.locomotives.get("l1").linesKeys.add("LEEDS_MANCHESTER");
//        railSystem.locomotives.get("l1").linesKeys.add("MANCHESTER_LIVERPOOL");
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
        for (Station station : railSystem.stations.values()) {
            first13stations.add(station.name);
            countStation++;
            if (countStation == 13) {
                break;
            }
        }

        LinkedList<String> first13paths = new LinkedList<>();
        int countPath = 0;
        for (Path path : railSystem.paths.values()) {
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
            this.trains.put(ptTrainName, new Train(ptTrainName));
            this.trains.get(ptTrainName).setHomeStationName(first13stations.get(i));
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
            this.trains.put(ctTrainName, new Train(ctTrainName));
            this.trains.get(ctTrainName).setHomeStationName(first13stations.get(i));
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

}




package p1;

import p1.Cars.Car;
import p1.Enums.Locations;

import java.util.*;

public class RailSystem {
    public RailSystem() {
        addDefaultLocations();
        // im więcej razy tym mniej szans na powstanie stacji z tylko 1 linią
        stationsAndLinks();
//        stationsAndLinks();
//        stationsAndLinks();
    }

    public Queue<String> alerts = new LinkedList<>();
    Map<String, Station> stations = new LinkedHashMap<>();
    Map<String, Locomotive> locomotives = new LinkedHashMap<>();
    Map<String, Car> cars = new LinkedHashMap<>();
    Map<String, Line> lines = new LinkedHashMap<>();

    Map<String, Path> paths = new LinkedHashMap<>();

    Map<String, Train> trains = new LinkedHashMap<>();



    void stationsAndLinks() {
        // tworzę wszystkie domyślne stacje

        // tworze listę gdzie 1 pozycja to klucz i wartość z mapy

        List<Map.Entry<String, Station>> stationEntries = new ArrayList<>(stations.entrySet());
        stationEntries.sort(Comparator.comparing(Map.Entry::getKey)); //sortowanie po nazwie

        Random random = new Random();
        Set<String> createdConnections = new LinkedHashSet<>(); // set do przechowywania już utworzonych połączeń


        // Tworzenie połączenia od każdej stacji
        for (int i = 0; i < stationEntries.size(); i++) {
            String key1 = stationEntries.get(i).getKey();
            Station station1 = stationEntries.get(i).getValue();

            String key2 = "";
            Station station2;

            // Szukanie innej stacji, która jeszcze nie ma połączenia z aktualną stacją
            do {
                int i2 = random.nextInt(stations.size()-1) +1;
                if (i2 != i) {
                    key2 = stationEntries.get(i2).getKey();
                    station2 = stationEntries.get(i2).getValue();
                }
            } while (createdConnections.contains(key1 + "_" + key2)||key2.equals("")); // Sprawdzanie, czy połączenie już istnieje

            // Tworzenie zapisu w mapie z połączeniem
            String key = key1 + "_" + key2;
            String revertedKey = key2 + "_" + key1;
            Line line = new Line(key1, key2);
            Line revertedLine = new Line(key2, key1);
            lines.put(key, line);
            lines.put(revertedKey, revertedLine);
            createdConnections.add(key); // Dodanie połączenia do zbioru już utworzonych połączeń
            createdConnections.add(revertedKey); // Dodanie połączenia do zbioru już utworzonych połączeń
        }


        /// sprawdzenie jak to działa
        List<String> myStations = new LinkedList<>();
        LinkedHashSet<String> myHashSet = new LinkedHashSet<>(); // set do przechowywania już utworzonych połączeń

        for (Map.Entry<String, Station> entry : this.stations.entrySet()) {
            myStations.add(entry.getKey());
            for (String line : createdConnections) {
                if (line.startsWith(entry.getKey())){
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
            System.out.println(entry.getKey() + " - " + entry.getValue());
//            System.out.println(entry.getKey() + " - " + entry.getValue().station1Key+ " " + entry.getValue().station2Key);
        }

    }

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
}
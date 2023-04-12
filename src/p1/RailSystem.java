package p1;

import p1.Cars.Car;
import p1.Enums.Locations;

import java.util.*;

public class RailSystem {
    public RailSystem() {
        stationsAndLinks();
    }
    public Queue<String> alerts = new LinkedList();
    Map<String, Station> stations = new LinkedHashMap <>();
    Map<String, Locomotive> locomotives = new LinkedHashMap <>();
    Map<String, Car> cars = new LinkedHashMap <>();
    Map<String, Line> lines = new LinkedHashMap<>();

    Map<String, Train> trains = new HashMap<>();



    void stationsAndLinks() {
        // tworzę wszystkie domyślne stacje
        addDefaultLocations();

        // tworze listę gdzie 1 pozycja to klucz i wartość z mapy

        List<Map.Entry<String, Station>> stationEntries = new ArrayList<>(stations.entrySet());
        stationEntries.sort(Comparator.comparing(Map.Entry::getKey));        //sortowanie po nazwie


        Random random = new Random();
        // tworzę połaczenie między poszczególnymi stacjami

        for (int i = 0; i < 50; i++) {
            // while line size < 50
            //zabezpieczenie żeby nie dublować takich samych połaczeń
            int i1 = random.nextInt(stations.size()-2+1) + 1;
            int i2 = random.nextInt(i1);

            // tworzenie zapisu w mapie  z połączeniem
            String key1 = stationEntries.get(i1).getKey();
            Station station1 = stationEntries.get(i1).getValue();
            String key2 = stationEntries.get(i2).getKey();
            Station station2 = stationEntries.get(i2).getValue();
            String key = key1 + "_" + key2;
            String revertedKey = key2 + "_" + key1;
            Line line = new Line(key1, key2);
            Line revertedLine = new Line(key2, key1);
            lines.put(key, line);
            lines.put(revertedKey, revertedLine);
        }
    }

    void showStations() {
        System.out.println("Map of stations");
        System.out.println("Number of stations: "+ stations.size());

        for (Map.Entry<String, Station> entry : stations.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().name);
        }

    }

    void showLines() {
        System.out.println("Map of lines");
        System.out.println("Number of lines: "+ lines.size());

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
                "STOKE_ON_TRENT",
                "WOLVERHAMPTON",
                "DERBY",
                "BLACKPOOL",
                "IPSWICH",
                "NORWICH",
                "BOURNEMOUTH",
                "SOUTHEND_ON_SEA",
                "MILTON_KEYNES",
                "NORTHAMPTON",
                "PETERBOROUGH",
                "LINCOLN",
                "CHESTER",
                "LANCASTER",
                "WAKEFIELD",
                "ROTHERHAM",
                "DONCASTER",
                "BARNSLEY",
                "STOCKTON_ON_TEES",
                "SUNDERLAND",
                "WIGAN",
                "OLDHAM",
                "ROCHDALE",
                "BOLTON",
                "WARRINGTON",
                "ST_HELENS",
                "HALIFAX",
                "HUDDERSFIELD",
                "BLACKBURN",
                "BURNLEY",
                "PRESTON",
                "CHORLEY",
                "WIDNES",
                "LYTHAM_ST_ANNES",
                "FLEETWOOD",
                "POULTON_LE_FYLDE",
                "GARSTANG",
                "LONGRIDGE",
                "ORMSKIRK",
                "SKELMERSDALE",
                "LANCASHIRE",
                "RIBBLE_VALLEY",
                "CLITHEROE",
                "PREESALL",
                "KIRKHAM",
                "THORNTON_CLEVELEYS",
                "BISPHAM",
                "KIRKBY_LONSDALE",
                "ULVERSTON",
                "KENDAL",
                "GRANGE_OVER_SANDS",
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
                "NEWBIGGIN_BY_THE_SEA",
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
                "HEBDEN_BRIDGE",
                "SOWERBY_BRIDGE",
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
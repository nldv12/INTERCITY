package p1;

import p1.Cars.Car;

import java.util.LinkedList;
import java.util.List;

public class Presentation {
    public static void main(String[] args) {
        System.out.println("hi");
        System.out.println("Presentation");
        System.out.println("""
                Tutaj można znaleźć sporą część funkcjonalności które posiada ten program, 
                żeby zobaczyć wszytkie funkcjonalności zachęcam do własnoręcznego testowania tego programu
                uruchamiając metodę main z pliku main, tutaj niema zadnych walidacji ani całego interfejsu urzytkownika
                który zaimplementowałem jako niezależny wątek w klasie Task CLI (Command Line Interface)
                                """);

        RailSystem railSystem = RailSystem.getRailSystem();
        System.out.println("Tutaj zostaje utworzony obiekt klasy RailSystem wykorzystując Singleton ");
        System.out.println("""
                Singleton to wzorzec projektowy w języku Java, który zapewnia, że dana klasa posiada tylko jedną 
                instancję w całej aplikacji i udostępnia globalny punkt dostępu do tej instancji.
                                """);

        System.out.println("Domyślnie można wypełnić railSystem jakimiś obiektami zeby zobaczyć jak to wszytko funkcjonuje");
//        railSystem.addDefaultLocations();// dodajemy 100 przykłądowych stacji
//        railSystem.defaultRailSystemFill(25); // tworzymy trasy, linie, i 25 pociągów z wagonami

        System.out.println("Oczywiście można tworzyć ręcznie każdy z takich obiektów");

        System.out.println("Tworzenie stacji kolejowych");
        railSystem.addStation("WARSZAWA");
        System.out.println(railSystem.stations.get("WARSZAWA").name);// odwołanie bezpośrednie

        System.out.println("Tworzenie lokomotywy");
        railSystem.addNotReadyLocomotive("Lokomotywa1", "WARSZAWA");
        System.out.println(railSystem.notReadyLocomotives.get("Lokomotywa1").getName());// odwołanie bezpośrednie
        System.out.println("Tworzenie wagonu");
        LinkedList<String> carsNames = new LinkedList<>();
        railSystem.addCar("Wagon1", true, "1", "WARSZAWA");
        railSystem.addCar("Wagon2", true, "2", "WARSZAWA");
        railSystem.addCar("Wagon3", true, "3", "WARSZAWA");
        railSystem.addCar("Wagon4", true, "4", "WARSZAWA");
        carsNames.add("Wagon1");
        carsNames.add("Wagon2");
        carsNames.add("Wagon3");
        carsNames.add("Wagon4");
        // nazwa. czy jest ciężarowy,typ wedłóg kolejności w plikach (w cli jest oczywiście do tego lista), stacja
        System.out.println(railSystem.cars.get("Wagon1").getName());// nazwa
        System.out.println(railSystem.cars.get("Wagon1").isCargo() ? "Wagon ciężarowy" : "Wagon osonowy");// ciężarowy czy osobowy
        System.out.println(railSystem.cars.get("Wagon1").getHomeStation());// stacja domowa

        System.out.println("Tworzenie Pociągu");
        railSystem.userAddTrain("CT_1000", "WARSZAWA", "Lokomotywa1", carsNames);
        System.out.println(railSystem.notReadyTrains.get("CT_1000").getKey());


        System.out.println("Tworzenie liinii - połączeń między 2ma stacjami");
        railSystem.addStation("GDAŃSK");
        railSystem.addLine("WARSZAWA_GDAŃSK", "WARSZAWA", "GDAŃŚK", 150);
        railSystem.addLine("GDAŃŚK_WARSZAWA", "GDAŃŚK", "WARSZAWA", 150);
        // kolejno nazwa lini stacja startowa, stacja docelowa i dystans w km między nimi
        System.out.println(railSystem.lines.get("WARSZAWA_GDAŃSK").getKey());// nazwa
        System.out.println(railSystem.lines.get("WARSZAWA_GDAŃSK").getDistance() + " km");// dystans

        System.out.println("Tworzenie tras dla pociągu");
        railSystem.addStation("GDAŃSK");
        railSystem.addStation("POZNAŃ");
        railSystem.addStation("WROCŁAW");
        railSystem.addStation("RZESZÓW");

        LinkedList<String> lines = new LinkedList<>();
        // tworrząc linie nie mozna zapomnieć o liniach powrotnych
        railSystem.addLine("GDAŃŚK_POZNAŃ", "GDAŃŚK", "POZNAŃ", 100);
        railSystem.addLine("POZNAŃ_GDAŃŚK", "POZNAŃ", "GDAŃŚK", 100);

        railSystem.addLine("POZNAŃ_WROCŁAW", "POZNAŃ", "WROCŁAW", 200);
        railSystem.addLine("WROCŁAW_POZNAŃ", "WROCŁAW", "POZNAŃ", 200);

        railSystem.addLine("WROCŁAW_RZESZÓW", "WROCŁAW", "RZESZÓW", 200);
        railSystem.addLine("RZESZÓW_WROCŁAW", "RZESZÓW", "WROCŁAW", 200);
        lines.add("WARSZAWA_GDAŃSK");
        lines.add("GDAŃŚK_POZNAŃ");
        lines.add("POZNAŃ_WROCŁAW");
        lines.add("WROCŁAW_RZESZÓW");
        railSystem.addPath("Lokomotywa1", "WARSZAWA__RZESZÓW",
                "WARSZAWA", lines, "CT_1000");
        // w tym momencie pociąg jest gotowy aby wyryszyć w trasę

        System.out.println("Trasa: " + railSystem.paths.get("WARSZAWA__RZESZÓW").getPathKey());// trasa
        System.out.println("Linie trasy: ");
        for (String linia : railSystem.paths.get("WARSZAWA__RZESZÓW").linesKeys) {
            System.out.println(linia);
        }


        System.out.println("Program posiada funkcjonalność opartą na działaniu wątków");
        System.out.println("Istnieje wątek który zmienia prędkość lokomotywy losowo o 3 procent co sekendę");
        // najpierw tworzymy obiekt zadania które ma być spełnione:
        Task_LocomotiveSpeedChange task_LocomotiveSpeedChange = new Task_LocomotiveSpeedChange();
        // tworzymy wątek który będzie wykonywac nasze zadanie:
        Thread locomotiveSpeedChange = new Thread(task_LocomotiveSpeedChange);
        // uruchamiamy wątek
        System.out.println();
        System.out.println("Od tego momentu nasz progam nigdy nie zakończy swojego działania chyba ze wypadnie wyjątek");
        locomotiveSpeedChange.start();
        // żeby pokazać jego dizałanie trzeba wyświetlić prędkość lokomotywy co będzie skutkować
        // zasypaniem konsoli więc proponuję zakomentować uruchomienie wątku albo pętlę while
        //  aby nam to nie przeszkadzało później
        int count = 0;
        while (count<10){
            // uwaga! pętla działa dużo szybciej niż wątek zmieniajacy prędkość
            // więc komunikat pojawia się duużo częściej niż zmiana faktycznej prędkości
            System.out.println(railSystem.locomotives.get("Lokomotywa1").getCurrentSpeed());
            count++;
        }
        System.out.println("W tym wątku istnieje też mechanizm który wyłapuje informację o tym że lokomotywa");
        System.out.println("przekroczyła 200 km/h i wyświetla stosowny komunikat, możemy to sprawdzić:");
        // w railSystem posiadamy kolejkę wszytkich alertów i mozemy je tutaj wyświetlić
        boolean succes = false;
        while (!succes) {
            railSystem.locomotives.get("Lokomotywa1").setCurrentSpeed(250);
            if (railSystem.alerts.size() > 0) {
                String alert = railSystem.alerts.poll();
                System.out.println(alert);
                succes = true;
            }
        }
        System.out.println("Program posiada również wątek generujący raport o każdym z pociągów co 5 sekund");
        System.out.println("Taki raport jest umieszczany w pliku AppState.txt w tym samym katalogu co plik Presentation.java");
        Task_AppState taskAppState = new Task_AppState();
        Thread appState = new Thread(taskAppState);
        appState.start();
        System.out.println("""
                
                Najważniejszym wątkiem w tym programie jest wątek odpowiedzialny za ruch pociągów
                za jego sprawą pociągi zmieniają pzebytą odległość unikają kolizji czekając na 
                stacji póki linia się uwolni, a po dotarciu do stacji końcowej jest dla pociągu
                ustalana odwrotna trasa do tej którą przebył, jak również kiedy ponownie dotrze 
                do stacji z której wyjeższał to ponownie otrzymuje zaplanowaną trasę.
                
                                """);
        Task_TrainsMovement task_trainsMovement = new Task_TrainsMovement();
        Thread trainsMovement = new Thread(task_trainsMovement);
        trainsMovement.start();
        System.out.println("""
                
                Jest też oczywiście wątek CLI ale nie będziemy go tutaj uruchamiać ze względu na to
                że prowadzi on interakcję z użytkownikiem przez konsolę - Command Line Interface                
                                """);
        
    }
}

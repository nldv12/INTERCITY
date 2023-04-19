package p1;

import p1.Cars.Car;

public class Presentation {
    public static void main(String[] args) {
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
        railSystem.addDefaultLocations();// dodajemy 100 przykłądowych stacji
        railSystem.defaultRailSystemFill(); // tworzymy trasy, linie, i 26 pociągów z wagonami

        System.out.println("Oczywiście można tworzyć ręcznie każdy z takich obiektów");

        System.out.println("Tworzenie stacji kolejowych");
        railSystem.addStation("WARSZAWA");
        System.out.println(railSystem.stations.get("WARSZAWA"));// odwołanie bezpośrednie



        System.out.println("Oto lista funkcjonalnoći jakie posiada program");

    }
}

package p1;

import p1.Cars.Car;

public class Presentation {
    public static void main(String[] args) {
        System.out.println("Presentation");
//        RailSystem railSystem = new RailSystem();

        RailSystem railSystem = RailSystem.getRailSystem();
//        while (true){
        System.out.println("Tak działa RailSystem");
        System.out.println();

        railSystem.showStations();              // zobacz stacje
        System.out.println();
        railSystem.showLines();                 // zobacz linie


    }
}

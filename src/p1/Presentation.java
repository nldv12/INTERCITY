package p1;

import p1.Cars.Car;

public class Presentation {
    public static void main(String[] args) {
        System.out.println("Presentation");
        RailSystem testRailSystem = new RailSystem();
//        while (true){
        System.out.println("Tak działa RailSystem");
        System.out.println();

        testRailSystem.showStations();              // zobacz stacje
        System.out.println();
        testRailSystem.showLines();                 // zobacz linie


    }
}

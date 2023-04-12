package p1;

import p1.Cars.Car;

import java.util.ArrayList;

public class Station {
    String name;
    ArrayList<Locomotive> listOfLocomotives; // Тут має бути фільтр а не реальний список?
    ArrayList<Car> listOfCars;
    ArrayList<Train> listOfTrainsHome;


    ArrayList<Train> listOfTrainsStopping;

    ;


    public Station(String name) {
        this.name = name;
    }
}

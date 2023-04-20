package p1;

import p1.Cars.Car;

import java.util.ArrayList;
import java.util.List;

public class Station {
    String name;
    ArrayList<Locomotive> listOfLocomotives; // Тут має бути фільтр а не реальний список?
    ArrayList<Car> listOfCars;
    List<String> trainsNamesHome;


    ArrayList<Train> listOfTrainsStopping;
    public void showTrainsNamesHome(){
        for (String train : trainsNamesHome) {
            System.out.println(train);
        }
    }


    public Station(String name) {
        this.name = name;
    }
}

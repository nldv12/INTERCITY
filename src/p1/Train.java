package p1;

import p1.Cars.*;
import p1.Cars.CargoCars.BasicCargoCar;
import p1.Exeptions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Train {
    public Train(String key, String homeStationName) {
        this.key = key;
        this.homeStationName = homeStationName;
        this.isCargo = key.contains("CT");

    }
    private boolean isCargo;
    double position;
    private final String key;
    private String homeStationName;
    private String locomotiveName;
    List<String> carsNames = new LinkedList<>();


    void showCars() {
        for (String car : carsNames) {
            System.out.println(car);
        }
    }

    public boolean isCargo() {
        return isCargo;
    }

    // Getery
    public String getLocomotiveName() {
        return locomotiveName;
    }
    public String getHomeStationName() {
        return homeStationName;
    }

    // Setery
    void setlocomotiveName(String name) {
        this.locomotiveName = name;
    }
    public void setHomeStationName(String homeStationName) {
        this.homeStationName = homeStationName;
    }

    @Override
    public String toString() {
        return "Pociąg: " + this.key
                + " Lokomotywa: "
                + this.locomotiveName
                + " ilosc wagonów: "
                + this.carsNames.size()
                + " stacja macierzysta: "+ this.homeStationName;
    }


    public String getKey() {
        return key;
    }
}

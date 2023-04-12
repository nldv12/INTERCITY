package p1;

import p1.Cars.*;
import p1.Cars.CargoCars.BasicCargoCar;
import p1.Exeptions.*;

import java.util.ArrayList;
import java.util.List;

public class Train {
    public Train(String key) {
        this.key = key;
    }

    double position;
    String key;
    private String locomotiveName;
    List<String> carsNames = new ArrayList<>();
    Path path;



    void setlocomotiveName(String name) {
        this.locomotiveName = name;
    }



    void showCars() {
        for (String car : carsNames) {
            System.out.println(car);
        }
    }



    @Override
    public String toString() {
        return "Pociąg: " + this.key + " Lokomotywa: " + this.locomotiveName + " ilosc wagonów: " + this.carsNames.size();
    }

    public String getLocomotiveName() {
        return locomotiveName;
    }
}

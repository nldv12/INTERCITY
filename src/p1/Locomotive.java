package p1;

import java.util.Random;

public class Locomotive {
    public Locomotive(String name, String homeStation) {
        this.id = this.hashCode();
        this.name = name;
        this.homeStation = homeStation;
    }

    // moje pola --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    String name;
    boolean isMoving;

    // kluczowe pola (treść zadania)--------------------------------------------------------------------------------------------------------------------------------------------------------------

    private String homeStation;
    private String sourceStation;
    private String destinationStation;
    int id;
    static int currentSpeed = 100;

    static void speedUp(int value){
        currentSpeed = value;
    }

    static void slowDown(int value){
        currentSpeed = value;
    }
    public static void randomSpeedChange() {
        Random random = new Random();
        if (random.nextBoolean()) {
            double increase = currentSpeed * 0.03;
            currentSpeed += increase;
        } else {
            double decrease = currentSpeed * 0.03;
            currentSpeed -= decrease;
        }
    }

    // Setters --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }
    public void setHomeStation(String homeStation) {
        this.sourceStation = homeStation;
    }


    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }


    // Getters ------------------------------------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getHomeStation() {
        return homeStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }


    public int getMaxCarNumber() {
        return 20;
    }
    public int getMaxPullWeight() {
        return 1000;
    }
    public int getMaxElectricCarsNumber() {
        return 10;
    }


    @Override
    public String toString() {
        return "" + id;
    }


}

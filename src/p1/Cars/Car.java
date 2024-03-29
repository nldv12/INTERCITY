package p1.Cars;

import p1.Enums.Shippers;

import java.util.Optional;

public abstract class Car {
    public Car(String name,boolean isCargo, String homeStation) {
        this.name = name;
        this.isCargo = isCargo;
        this.id = this.hashCode();
        this.homeStation = homeStation;
        this.grossWeight = this.netWeight;
    }

    protected String name;
    public String type;
    String homeStation = "";
    protected boolean isCargo;
    protected int netWeight;
    protected int grossWeight;
    public boolean needElectricity;
    public int id;
    String zabezpieczenia;
    private String trainKey = "";
    boolean isEmpty;
    String locomotive;



    // Settery
    public void setCargo(boolean cargo) {
        isCargo = cargo;
    }
    public void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }
    public void setTrainKey(String trainKey) {
        this.trainKey = trainKey;
    }
    public void setGrossWeight(int grossWeight) {
        this.grossWeight = grossWeight;
    }
    public void incGrossWeight(int weight) {
        this.grossWeight += weight;
    }

    //Gettery

    public int getNetWeight(){
        return this.netWeight;
    }
    public int getGrossWeight(){
        return this.grossWeight;
    }
    public String getHomeStation() {
        return homeStation;
    }
    public String getTrainKey() {
        return trainKey;
    }
    public boolean isCargo() {
        return isCargo;
    }
    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return  "Typ: "+type+" id: "+this.id;
    }
}

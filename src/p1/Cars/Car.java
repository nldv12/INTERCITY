package p1.Cars;

import p1.Enums.Shippers;

import java.util.Optional;

public abstract class Car {
    public Car(String homeStation) {
        this.id = this.hashCode();
        this.homeStation = homeStation;
    }
    String type;
    String homeStation;
    protected boolean isCargo;
    protected int netWeight;
    int grossWeight;
    public boolean needElectricity;
    public int id;
    String zabezpieczenia;
    protected String trainKey;
    boolean isEmpty;

    String locomotive;

    Optional<String> trainKeyO;

    public int getNetWeight(){
        return this.netWeight;
    }
    public int grossWeight(){
        return this.grossWeight;
    }


    @Override
    public String toString() {
        return  "Typ: "+type+" id: "+this.id;
    }


    public boolean getIsCargo() {
        return isCargo;
    }

    public void setCargo(boolean cargo) {
        isCargo = cargo;
    }

    public String getHomeStation() {
        return homeStation;
    }

    public void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }


    public String getTrainKey() {
        return trainKey;
    }

    public void setTrainKey(String trainKey) {
        this.trainKey = trainKey;
    }
}

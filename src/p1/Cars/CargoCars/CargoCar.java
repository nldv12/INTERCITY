package p1.Cars.CargoCars;

import p1.Cars.Car;

public abstract class CargoCar extends Car {
    public CargoCar(String name,boolean isCargo, String homeStation) {
        super(name,isCargo,homeStation);
        this.type = "BasicFreightCar";
        this.id = this.hashCode();
        this.netWeight = 20;
        this.needElectricity = false;
        this.isCargo = true;
        this.grossWeight = this.netWeight;
    }



    String type = "FreightCar";

    @Override
    public String toString() {
        return  "Typ: "+type+" id: "+this.id;
    }
}

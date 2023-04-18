package p1.Cars.CargoCars;

public class BasicCargoCar extends CargoCar {
    public BasicCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "BasicFreightCar";
        this.id = this.hashCode();
        this.netWeight = 20;
        this.needElectricity = false;
        this.isCargo = true;

    }


}

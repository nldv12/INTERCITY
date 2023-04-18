package p1.Cars.CargoCars;

public class HeavyCargoCar extends CargoCar {
    public HeavyCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "HeavyFreightCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = false;
        this.isCargo = true;

    }


}

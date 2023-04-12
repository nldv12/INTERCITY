package p1.Cars.CargoCars;

public class ToxicMaterialCargoCar extends CargoCar {
    public ToxicMaterialCargoCar(String homeStation) {
        super(homeStation);
        this.type = "ToxicMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 25;
        this.needElectricity = false;

    }
}

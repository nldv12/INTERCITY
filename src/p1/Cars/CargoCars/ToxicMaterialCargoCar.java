package p1.Cars.CargoCars;

public class ToxicMaterialCargoCar extends CargoCar {
    public ToxicMaterialCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "ToxicMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 25;
        this.needElectricity = false;
        this.isCargo = true;

    }
}

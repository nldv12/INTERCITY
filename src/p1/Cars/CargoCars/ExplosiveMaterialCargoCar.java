package p1.Cars.CargoCars;

public class ExplosiveMaterialCargoCar extends CargoCar {
    public ExplosiveMaterialCargoCar(String homeStation) {
        super(homeStation);
        this.type = "ExplosiveMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = false;
        this.isCargo = true;


    }

}

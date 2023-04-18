package p1.Cars.CargoCars;

public class ExplosiveMaterialCargoCar extends CargoCar {
    public ExplosiveMaterialCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "ExplosiveMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = false;
        this.isCargo = true;


    }

}

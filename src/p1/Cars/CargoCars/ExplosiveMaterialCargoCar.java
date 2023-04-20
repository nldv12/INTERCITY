package p1.Cars.CargoCars;

public class ExplosiveMaterialCargoCar extends CargoCar {
    public ExplosiveMaterialCargoCar(String name,boolean isCargo, String homeStation) {
        super(name,isCargo,homeStation);
        this.type = "ExplosiveMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = false;
        this.isCargo = true;


    }

}

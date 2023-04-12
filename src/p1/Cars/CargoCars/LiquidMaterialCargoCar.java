package p1.Cars.CargoCars;

public class LiquidMaterialCargoCar extends CargoCar {
    public LiquidMaterialCargoCar(String homeStation) {
        super(homeStation);
        this.type = "LiquidMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 25;
        this.needElectricity = false;
    }

}

package p1.Cars.CargoCars;

public class LiquidMaterialCargoCar extends CargoCar {
    public LiquidMaterialCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "LiquidMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 25;
        this.needElectricity = false;
        this.isCargo = true;

    }

}

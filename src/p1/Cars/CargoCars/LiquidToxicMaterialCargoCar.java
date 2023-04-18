package p1.Cars.CargoCars;

public class LiquidToxicMaterialCargoCar extends CargoCar {
    public LiquidToxicMaterialCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "LiquidToxicMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = false;
        this.isCargo = true;


    }

}

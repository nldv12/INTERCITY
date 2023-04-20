package p1.Cars.CargoCars;

public class GasMaterialCargoCar extends CargoCar {
    public GasMaterialCargoCar(String name,boolean isCargo, String homeStation) {
        super(name,isCargo,homeStation);
        this.type = "GasMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 20;
        this.needElectricity = false;
        this.isCargo = true;

    }


}

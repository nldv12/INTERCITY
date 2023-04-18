package p1.Cars.CargoCars;

public class GasMaterialCargoCar extends CargoCar {
    public GasMaterialCargoCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "GasMaterialFreightCar";
        this.id = this.hashCode();
        this.netWeight = 20;
        this.needElectricity = false;
        this.isCargo = true;

    }


}

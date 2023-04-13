package p1.Cars.CargoCars;

public class RefrigeratedCargoCar extends CargoCar {
    public RefrigeratedCargoCar(String homeStation) {
        super(homeStation);
        this.type = "RefrigeratedFreightCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = true;
        this.isCargo = true;


    }

}

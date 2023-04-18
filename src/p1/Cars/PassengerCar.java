package p1.Cars;

public class PassengerCar extends Car {
    public PassengerCar(String name,String homeStation) {
        super(name, homeStation);
        this.type = "PassengerCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.grossWeight = this.netWeight;
        this.needElectricity = true;
        this.isCargo = false;
    }
    int numberOfSeats;



}

package p1.Cars;

public class PassengerCar extends Car {
    public PassengerCar(String homeStation) {
        super(homeStation);
        this.type = "PassengerCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = true;
        this.isCargo = false;
    }
    int numberOfSeats;



}

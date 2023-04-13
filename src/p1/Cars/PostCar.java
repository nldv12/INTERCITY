package p1.Cars;

public class PostCar extends Car {
    public PostCar(String homeStation) {
        super(homeStation);
        this.type = "PostCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = true;
        this.isCargo = false;
    }





}

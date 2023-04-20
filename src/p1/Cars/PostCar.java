package p1.Cars;

public class PostCar extends Car {
    public PostCar(String name,boolean isCargo, String homeStation) {
        super(name,isCargo,homeStation);
        this.type = "PostCar";
        this.id = this.hashCode();
        this.netWeight = 30;
        this.needElectricity = true;
        this.isCargo = false;
    }





}

package p1.Cars;

public class RestaurantCar extends Car {
    public RestaurantCar(String name,boolean isCargo, String homeStation) {
        super(name,isCargo,homeStation);
        this.type = "RestaurantCar";
        this.id = this.hashCode();
        this.netWeight = 35;
        this.needElectricity = true;
        this.isCargo = false;
    }







}

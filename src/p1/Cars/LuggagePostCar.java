package p1.Cars;

public class LuggagePostCar extends PostCar{
    public LuggagePostCar(String homeStation) {
        super(homeStation);
        this.type = "LuggagePostCar";
        this.id = this.hashCode();
        this.netWeight = 25;
        this.needElectricity = false;
        this.isCargo = false;

    }

}

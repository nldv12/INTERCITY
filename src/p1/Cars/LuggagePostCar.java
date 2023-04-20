package p1.Cars;

public class LuggagePostCar extends PostCar{
    public LuggagePostCar(String name,boolean isCargo, String homeStation) {
        super(name,isCargo,homeStation);
        this.name = name;
        this.type = "LuggagePostCar";
        this.id = this.hashCode();
        this.netWeight = 25;
        this.needElectricity = false;
        this.isCargo = false;

    }

}

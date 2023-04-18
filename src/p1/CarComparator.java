package p1;

import p1.Cars.Car;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {
    @Override
    public int compare(Car car1, Car car2) {
        // Sortowanie rosnąco, na podstawie wagi brutto
        return car1.getGrossWeight() - car2.getGrossWeight();
    }
}

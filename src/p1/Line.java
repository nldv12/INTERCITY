package p1;

import java.util.ArrayList;

public class Line {
    ArrayList<Train> listOfTrains;
    String station1Key;
    String station2Key;
    String key;
    private int distance;

    public Line(String station1Key, String station2Key) {
        this.station1Key = station1Key;
        this.station2Key = station2Key;
        this.key = station1Key + "_" + station2Key;
    }

    @Override
    public String toString() {
        return key;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

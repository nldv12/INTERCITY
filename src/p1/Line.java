package p1;

import java.util.ArrayList;

public class Line {
    public Line(String station1Key, String station2Key) {
        this.station1Key = station1Key;
        this.station2Key = station2Key;
        this.key = station1Key + "_" + station2Key;
    }

    ArrayList<Train> listOfTrains;
    String station1Key;
    String station2Key;
    private String key;
    private int distance;

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

    public String getKey() {
        return key;
    }
}

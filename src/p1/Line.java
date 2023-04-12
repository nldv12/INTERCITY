package p1;

import java.util.ArrayList;

public class Line {
    ArrayList<Train> listOfTrains;
    String station1Key;
    String station2Key;

    public Line(String station1Key, String station2Key) {
        this.station1Key = station1Key;
        this.station2Key = station2Key;
    }

    @Override
    public String toString() {
        return station1Key + " to " + station2Key;
    }
}

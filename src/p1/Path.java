package p1;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Path {
    public Path(String pathKey, LinkedList<String> linesKeys) {
        this.linesKeys = linesKeys;
        this.pathKey = pathKey;
    }
    private String pathKey;
    private int totalPathDistance;
    List<String> trainsKeys = new LinkedList<>();
    List<String> linesKeys;

    // Getters
    public int getTotalPathDistance() {
        return totalPathDistance;
    }
    public void setPathKey(String pathKey) {
        this.pathKey = pathKey;
    }
    // Setters
    public String getPathKey() {
        return pathKey;
    }
    public void setTotalPathDistance(int totalPathDistance) {
        this.totalPathDistance = totalPathDistance;
    }



}

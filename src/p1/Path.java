package p1;

import java.util.LinkedList;
import java.util.List;

public class Path {
    public Path(String pathKey, LinkedList<String> linesKeys) {
        this.linesKeys = linesKeys;
        this.pathKey = pathKey;
    }
    private String pathKey;
    List<String> trainsKeys = new LinkedList<>();
    List<String> linesKeys;


    public void setPathKey(String pathKey) {
        this.pathKey = pathKey;
    }

    public String getPathKey() {
        return pathKey;
    }
}

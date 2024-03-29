package p1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Locomotive {
    RailSystem railSystem = RailSystem.getRailSystem();

    public Locomotive(String name, String homeStation) {
        this.id = this.hashCode();
        this.name = name;
        this.homeStation = homeStation;


    }

    // moje pola --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    String name;
    private String trainKey;
    private boolean isMoving;

    Optional<String> currentLineKey = Optional.empty();
    long currentArrivalTime;
    Optional<String> currentStation = Optional.empty();

    private double distancePassedTotal = 0;
    private double distancePassedLocal = 0;

    // kluczowe pola (treść zadania)--------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String homeStation = "";
    private String sourceStation;
    private String destinationStation;
    int id;
    private double currentSpeed = 100;
    private String pathKey;
    List<String> linesKeys = new LinkedList<>();

    public void reverseLines() {
        List<String> newList = new LinkedList<>(railSystem.getPathLinesByPathKey(pathKey));

        String newStartStation = "";
        String newEndStation = "";

        LinkedList<String> reversedList = new LinkedList<>();
        for (int i = newList.size() - 1; i >= 0; i--) {
            String line = newList.get(i);
            String[] cities = line.split("_");
            reversedList.add(cities[1] + "_" + cities[0]);
            if (i == newList.size() - 1)
                newStartStation = cities[1];
            if (i == 0)
                newEndStation = cities[0];
        }
        this.linesKeys = reversedList;


    }

    public synchronized void randomSpeedChange() {
        Random random = new Random();
        if (random.nextBoolean()) {
            double increase = currentSpeed * 0.03;
            currentSpeed += increase;
        } else {
            double decrease = currentSpeed * 0.03;
            currentSpeed -= decrease;
        }
    }
// on line to station
    public void moveLocomotiveOnLine(long now, long deltaTime) {

        double vPerMilis = currentSpeed / 3600000; // Obliczenie dystansu pokonanego za 1 milisekundę
        double deltaDistance = vPerMilis * deltaTime;
        this.distancePassedLocal += deltaDistance;
        if (distancePassedLocal >= railSystem.getLineByKey(currentLineKey.get()).getDistance()) {
            int totalPathDistance = railSystem.paths.get(getPathKey()).getTotalPathDistance();
            if (distancePassedTotal > totalPathDistance)
                distancePassedTotal = totalPathDistance;
            else
                this.distancePassedTotal += railSystem.getLineByKey(currentLineKey.get()).getDistance();
            String nextStation = railSystem.lines.get(currentLineKey.orElse("")).station2Key;
            currentStation = Optional.of(nextStation);
            currentLineKey = Optional.empty();
            currentArrivalTime = now;
            setMoving(false);
            if (currentStation.equals(Optional.of(destinationStation))) {
                reverseLines();
                distancePassedTotal = 0;
            } else if (currentStation.equals(Optional.of(sourceStation))) {
                linesKeys = new LinkedList<>(railSystem.getPathLinesByPathKey(pathKey));
                distancePassedTotal = 0;
            }
        }else {
            this.distancePassedTotal += deltaDistance;
        }
    }
    // from station to line
    public void moveLocomotiveOnStation(long now, long deltaTime) {

        boolean timeToGo = false;
        if (currentStation.equals(destinationStation)) {
            timeToGo = now - currentArrivalTime > 30000;
        } else if (currentStation.equals(sourceStation)) {
            timeToGo = now - currentArrivalTime > 30000;
        } else {
            timeToGo = now - currentArrivalTime > 2000;
        }
        String lineKey = linesKeys.get(0);
        if (timeToGo && RailSystem.getRailSystem().isLineEmpty(lineKey)) {
            linesKeys.remove(0);
            currentStation = Optional.empty();
            currentLineKey = Optional.of(lineKey);
            setMoving(true);
            distancePassedLocal = 0;
        }
    }

    public synchronized void moveLocomotive(long now, long deltaTime) {

        if (currentStation.isPresent()) {
            moveLocomotiveOnStation(now, deltaTime);
        } else if (currentLineKey.isPresent()) {
            moveLocomotiveOnLine(now, deltaTime);
        }
    }







    // Getters ------------------------------------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getHomeStation() {
        return homeStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public int getMaxCarNumber() {
        return 20;
    }

    public int getMaxPullWeight() {
        return 100;
    }

    public int getMaxElectricCarsNumber() {
        return 10;
    }

    public String getPathKey() {
        return pathKey;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public double getDistancePassedPercantageTotal() {
        return distancePassedTotal * 100 / getTotalPathDistance();
    }

    public double getDistancePassedPercantageLocal() {
        return distancePassedLocal * 100 / getTotalPathDistance();
    }

    public String getTrainKey() {
        return trainKey;
    }

    public int getTotalPathDistance() {
        return railSystem.paths.get(getPathKey()).getTotalPathDistance();
    }

    public Optional<String> getCurrentLineKey() {
        return currentLineKey;
    }

    public Optional<String> getCurrentStation() {
        return currentStation;
    }







    // Setters --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public synchronized void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public synchronized void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }

    public synchronized void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public synchronized void setPathKey(String pathKey) {
        this.pathKey = pathKey;
    }
    public synchronized void setLinesKeys(String pathKey) {
        this.linesKeys = new LinkedList<String>(RailSystem.getRailSystem().getPathByKey(pathKey).linesKeys);
    }
    public synchronized void setLinesKeysByList(LinkedList<String> linesKeys) {
        this.linesKeys.addAll(linesKeys);
    }



    public synchronized void setMoving(boolean moving) {
        isMoving = moving;
    }

    public synchronized void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public synchronized void calcAndSetDistancePassedPercantageLocal(double distancePassedPercantage) {
//        this.distancePassedPercantageLocal = distancePassedKM * 100 / totalPathDistance;
    }

    public synchronized void setTrainKey(String trainKey) {
        this.trainKey = trainKey;
    }
    public synchronized void setCurrentLineKey(Optional<String> currentLineKey) {
        this.currentLineKey = currentLineKey;
    }

    public synchronized void setCurrentStation(Optional<String> currentStation) {
        this.currentStation = currentStation;
    }

    @Override
    public String toString() {
        return "" + id;
    }


}

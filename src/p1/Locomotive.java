package p1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Locomotive {

    public Locomotive(String name, String homeStation) {
        this.id = this.hashCode();
        this.name = name;
        this.homeStation = homeStation;
    }

    // moje pola --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    String name;
    private String trainKey;
    private boolean isMoving;

    Optional<String> currentLineKey;
    long currentArrivalTime;
    Optional<String> currentStation;
    private int totalPathDistance;
    private int localLineDistance; /// not needed
    private double distancePassedTotal = 0;
    private double distancePassedLocal = 0;
    private double distancePassedPercantageTotal = 0;
    private double distancePassedPercantageLocal = 0;

    // kluczowe pola (treść zadania)--------------------------------------------------------------------------------------------------------------------------------------------------------------
    private String homeStation = "";
    private String sourceStation;
    private String destinationStation;
    int id;
    private int currentSpeed = 100;
    private String pathKey;
    List<String> linesKeys = new LinkedList<>();

    public synchronized void  randomSpeedChange() {
        Random random = new Random();
        if (random.nextBoolean()) {
            double increase = currentSpeed * 0.03;
            currentSpeed += increase;
        } else {
            double decrease = currentSpeed * 0.03;
            currentSpeed -= decrease;
        }
    }

    public void moveLocomotiveOnLine(long now, long deltaTime){
        RailSystem railSystem = RailSystem.getRailSystem();

        double vPerMilis = currentSpeed / 3600000; // Obliczenie dystansu pokonanego za 1 milisekundę
        double deltaDistance = vPerMilis * deltaTime;
        this.distancePassedLocal += deltaDistance;
        this.distancePassedTotal += deltaDistance;
        if (distancePassedLocal >= railSystem.getLineByKey(currentLineKey.get()).getDistance()){
            currentStation = Optional.of(railSystem.lines.get(currentLineKey).station2Key);
            currentLineKey = Optional.empty();
            currentArrivalTime = now;
            linesKeys.remove(0);
//            if (currentStation==finalstation){
//                revertedlinekeys
//            }
//            if (currentStation==startstation){
//                revertedlinekeys
//            }

        }
    }
    public void moveLocomotiveOnStation (long now, long deltaTime){
        // chek if final or start station
        boolean timeToGo = now - currentArrivalTime > 2000;// if final to 30 sec
        boolean nextlineEmpty = true;// implementacja
        String lineKey =  linesKeys.get(0);
        if (timeToGo && RailSystem.getRailSystem().isLineEmpty(lineKey)){
            currentStation = Optional.empty();
            currentLineKey = Optional.of(lineKey);
            distancePassedLocal = 0;
        }
    }
    public synchronized void moveLocomotive(long now, long deltaTime){
        if (currentStation.isPresent()){
            moveLocomotiveOnStation(now, deltaTime);
        } else if (currentLineKey.isPresent()) {
            moveLocomotiveOnLine(now,deltaTime);

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
    public int getCurrentSpeed() {
        return currentSpeed;
    }
    public double getDistancePassedPercantageTotal() {
        return distancePassedPercantageTotal;
    }
    public double getDistancePassedPercantageLocal() {
        return distancePassedPercantageLocal;
    }
    public String getTrainKey() {
        return trainKey;
    }
    public int getTotalPathDistance() {
        return totalPathDistance;
    }
    public int getLocalLineDistance() {
        return localLineDistance;
    }
    // Setters --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }
    public void setHomeStation(String homeStation) {
        this.homeStation = homeStation;
    }
    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }
    public void setPathKey(String pathKey) {
        linesKeys = List.copyOf(RailSystem.getRailSystem().getPathByKey(pathKey).linesKeys);
        this.pathKey = pathKey;
    }
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
    public void setDistancePassedPercantageTotal(double distancePassedPercantage) {
        this.distancePassedPercantageTotal = distancePassedPercantage;
    }
    public void setDistancePassedPercantageLocal(double distancePassedPercantage) {
        this.distancePassedPercantageLocal = distancePassedPercantage;
    }
    public void calcAndSetDistancePassedPercantageTotal(double distancePassedPercantage) {
        this.distancePassedPercantageTotal = distancePassedTotal * 100 / totalPathDistance;
    }
    public void calcAndSetDistancePassedPercantageLocal(double distancePassedPercantage) {
//        this.distancePassedPercantageLocal = distancePassedKM * 100 / totalPathDistance;
    }
    public void setTrainKey(String trainKey) {
        this.trainKey = trainKey;
    }
    public void setTotalPathDistance(int totalPathDistance) {
        this.totalPathDistance = totalPathDistance;
    }
    public void setLocalLineDistance(int localLineDistance) {
        this.localLineDistance = localLineDistance;
    }


    @Override
    public String toString() {
        return "" + id;
    }





}

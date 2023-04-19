package p1;

import p1.Exeptions.RailroadHazardException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Task_LocomotiveSpeedChange implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();
    @Override
    public void run() {
        while (true) {
            List<Locomotive> allLocomotives = new LinkedList<>(railSystem.getLocomotivesValues());
            for (Locomotive locomotive : allLocomotives) {
                locomotive.randomSpeedChange(); // Wywołanie metody zmieniającej prędkość
                try {
                    checkRailroadHazard(locomotive);
                } catch (RailroadHazardException e) {
                    railSystem.alerts.add(e.getMessage());
                    railSystem.getLocomotive(locomotive.getName()).setCurrentSpeed(100);
                }
                if (locomotive.getCurrentSpeed() < 60)
                    locomotive.setCurrentSpeed(100);

            }
            try {
                Thread.sleep(1000); // Czekanie na 1 sekundę
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void checkRailroadHazard(Locomotive locomotive) throws RailroadHazardException {
//        Map<String, Locomotive> allLocomotives = new LinkedHashMap<>(railSystem.getLocomotives());
//        for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
            String trainKey = railSystem.getLocomotive(locomotive.getName()).getTrainKey();
            String from = railSystem.getLocomotive(locomotive.getName()).getSourceStation();
            String to = railSystem.getLocomotive(locomotive.getName()).getDestinationStation();
            double speed = railSystem.getLocomotive(locomotive.getName()).getCurrentSpeed();
            if (speed >200){
//                locomotiveName = locomotive.getName();
                throw new RailroadHazardException("UWAGA! Pociąg: "+ trainKey + " przekroczył 200 km/h " +
                        "\n jedzie od: " + from + " do "+ to + " prędkość: " + (int)speed + " km/h");

//            }
        }
    }
}

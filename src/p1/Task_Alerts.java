package p1;

import p1.Exeptions.RailroadHazardException;

import java.util.LinkedHashMap;
import java.util.Map;

public class Task_Alerts implements Runnable{
    RailSystem railSystem = RailSystem.getRailSystem();
    String locomotiveName = "";
    @Override
    public void run() {

        while (true){
            try {
                checkRailroadHazard();
            } catch (RailroadHazardException e) {
                railSystem.alerts.add(e.getMessage());
                railSystem.getLocomotive(locomotiveName).setCurrentSpeed(100);
                /// w zadaniu tego niema można usunąć wtedy komunkat będzie się pojawiać cały czas

            }
//            while (! railSystem.alerts.isEmpty()) {
//                String alert = railSystem.alerts.poll();
////                System.out.println(alert);
//            }

//            for (String alert : railSystem.alerts) {
//                System.out.println(alert);
//            }


        }
    }
    public synchronized void checkRailroadHazard() throws RailroadHazardException{
//        Map<String, Locomotive> allLocomotives = new LinkedHashMap<>(railSystem.getLocomotives());
        for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
            String trainKey = railSystem.getLocomotive(entry.getKey()).getTrainKey();
            String from = railSystem.getLocomotive(entry.getKey()).getSourceStation();
            String to = railSystem.getLocomotive(entry.getKey()).getDestinationStation();
            double speed = railSystem.getLocomotive(entry.getKey()).getCurrentSpeed();
            if (speed >200){
                locomotiveName = entry.getKey();
                throw new RailroadHazardException("UWAGA! Pociąg: "+ trainKey + " przekroczył 200 km/h " +
                        "\n jedzie od: " + from + " do "+ to + " prędkość: " + (int)speed + " km/h");

            }
        }
    }

}

package p1;

import p1.Exeptions.RailroadHazardException;

import java.util.Map;

public class Task_Alerts implements Runnable{
    RailSystem railSystem = RailSystem.getRailSystem();
    @Override
    public void run() {
        while (true){
            try {
                checkRailroadHazard();
            } catch (RailroadHazardException e) {
                railSystem.alerts.add(e.getMessage());
            }
            for (String alert : railSystem.alerts) {
                System.out.println(alert);
            }


        }
    }
    public synchronized void checkRailroadHazard() throws RailroadHazardException{
        for (Map.Entry<String, Locomotive> entry : railSystem.locomotives.entrySet()) {
            String trainKey = railSystem.locomotives.get(entry.getKey()).getTrainKey();
            String from = railSystem.locomotives.get(entry.getKey()).getSourceStation();
            String to = railSystem.locomotives.get(entry.getKey()).getDestinationStation();
            double speed = railSystem.locomotives.get(entry.getKey()).getCurrentSpeed();
            if (speed >200){
                throw new RailroadHazardException("UWAGA! Pociąg: "+ trainKey + " przekroczył 200 km/h jedzie od: " + from + " do "+ to);
            }
        }
    }

}

package p1;

import java.util.LinkedList;
import java.util.List;

public class Task_LocomotiveSpeedChange implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();
    @Override
    public void run() {
        while (true) {
            List<Locomotive> allLocomotives = new LinkedList<>(railSystem.getLocomotivesValues());
            for (Locomotive locomotive : allLocomotives) {
                locomotive.randomSpeedChange(); // Wywołanie metody zmieniającej prędkość
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
}

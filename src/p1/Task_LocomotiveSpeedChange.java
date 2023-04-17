package p1;

public class Task_LocomotiveSpeedChange implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();
    @Override
    public void run() {
        while (true) {
            for (Locomotive locomotive : railSystem.locomotives.values()) {
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

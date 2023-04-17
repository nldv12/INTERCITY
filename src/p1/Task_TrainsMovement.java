package p1;

public class Task_TrainsMovement implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();

    @Override
    public void run() {
        try {
            long prevTime = System.currentTimeMillis();
            while (true) {
                long now = System.currentTimeMillis();
                long deltaTime = now - prevTime;
                for (Locomotive locomotive : railSystem.locomotives.values()) {
                    // Jeśli pociąg jest w ruchu
                    locomotive.moveLocomotive(now, deltaTime); // Przesunięcie lokomotywy na nową pozycję
                    // Sprawdzenie, czy pociąg dotarł do stacji końcowej
                    if (locomotive.getDistancePassedPercantageTotal() == 100) {
                        // odwrócenie trasy i wyruszenie w nową trasę
                    }
                }
                prevTime = now;
                Thread.sleep(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

}





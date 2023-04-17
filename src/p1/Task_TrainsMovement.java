package p1;

public class Task_TrainsMovement implements Runnable{
    RailSystem railSystem = RailSystem.getRailSystem();

    @Override
    public void run() {

        // Uruchomienie wątku symulującego ruch pociągów
            Thread simulationThread = new Thread(() -> {
                while (true) {
                    for (Locomotive locomotive : railSystem.locomotives.values()) {
                        if (locomotive.isMoving()) {
                            // Jeśli pociąg jest w ruchu
                            double distance = locomotive.getCurrentSpeed() / 3600; // Obliczenie dystansu pokonanego za 1 sekundę
                            locomotive.moveLocomotive(distance); // Przesunięcie lokomotywy na nową pozycję

                            // Sprawdzenie, czy pociąg dotarł do stacji końcowej
                            if (locomotive.getDistancePassedPercantageTotal() == 100) {
                                // odwrócenie trasy i wyruszenie w nową trasę
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            simulationThread.start();






    }



}

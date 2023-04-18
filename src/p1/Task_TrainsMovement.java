package p1;

public class Task_TrainsMovement implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();

    @Override
    public void run() {
        try {
            System.out.println();
            long prevTime = System.currentTimeMillis();
            int counter = 0;
            while (true) {
//            while (counter != 100) {
//                if (counter == 99) System.out.println();
                counter++;
                long now = System.currentTimeMillis();
                long deltaTime = now - prevTime;
//                long deltaTime = 100;
                for (Locomotive locomotive : railSystem.locomotives.values()) {
                    locomotive.moveLocomotive(now, deltaTime); // Przesunięcie lokomotywy na nową pozycję
                }
                prevTime = now;
                Thread.sleep(1);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

}





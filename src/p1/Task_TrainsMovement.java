package p1;

import java.util.LinkedList;
import java.util.List;

public class Task_TrainsMovement implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();

    @Override
    public void run() {
        try {
            long prevTime = System.currentTimeMillis();
            int counter = 0;
            while (true) {
//            while (counter != 100) {
//                if (counter == 99) System.out.println();
                counter++;
                long now = System.currentTimeMillis();
//                long deltaTime = now - prevTime;
                long deltaTime = 800;
                List<Locomotive> allLocomotives = new LinkedList<>(railSystem.getLocomotivesValues());
                for (Locomotive locomotive : allLocomotives) {
                    if (locomotive.getPathKey() != null){
                        railSystem.prepareBeforeTrip(locomotive.name, locomotive.getTrainKey());
                        locomotive.moveLocomotive(now, deltaTime); // Przesunięcie lokomotywy na nową pozycję
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





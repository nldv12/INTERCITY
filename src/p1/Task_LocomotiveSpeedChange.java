package p1;

public class Task_LocomotiveSpeedChange implements Runnable{
    @Override
    public void run() {
        while (true) {
            System.out.println(Locomotive.currentSpeed);
            try {
                Thread.sleep(1); // Czekanie na 1 sekundę
                Locomotive.randomSpeedChange(); // Wywołanie metody zmieniającej prędkość
                if (Locomotive.currentSpeed < 60)
                    Locomotive.speedUp(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

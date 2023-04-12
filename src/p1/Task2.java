package p1;

public class Task2 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("ID 2: " + i);
        }
    }
}

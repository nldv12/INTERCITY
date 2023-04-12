package p1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main");
        RailSystem railSystem = new RailSystem();
//        Locomotive l1 = new Locomotive();


        railSystem.alerts.add("Alert 1");
        railSystem.alerts.add("Alert 2");



        Task_LocomotiveSpeedChange Task_LocomotiveSpeedChange = new Task_LocomotiveSpeedChange();
        Task_CLI task_cli = new Task_CLI(railSystem);
        Task2 task2 = new Task2();

//        Thread LocomotiveSpeedChange = new Thread(Task_LocomotiveSpeedChange);
        Thread watek1 = new Thread(task_cli);
//        Thread watek2 = new Thread(task2);

//        LocomotiveSpeedChange.start();
        watek1.start();
//        watek2.start();





//thread with runable
    }
}
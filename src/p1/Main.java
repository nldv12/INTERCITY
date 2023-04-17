package p1;

public class Main {
    public static void main(String[] args) {
//        RailSystem railSystem = new RailSystem();
        System.out.println("Main");
        RailSystem railSystem = RailSystem.getRailSystem();
//        railSystem.alerts.add("Alert 1");
//        railSystem.alerts.add("Alert 2");

        Task_LocomotiveSpeedChange task_LocomotiveSpeedChange = new Task_LocomotiveSpeedChange();
        Task_CLI task_cli = new Task_CLI(railSystem);
        Task_TrainsMovement task_trainsMovement = new Task_TrainsMovement();
        Task_Alerts task_alerts = new Task_Alerts();
        Task2 task2 = new Task2();

        Thread locomotiveSpeedChange = new Thread(task_LocomotiveSpeedChange);
        Thread cli = new Thread(task_cli);
        Thread trainsMovement = new Thread(task_trainsMovement);
        Thread alerts = new Thread(task_alerts);
//        Thread watek2 = new Thread(task2);

//        locomotiveSpeedChange.start();
        cli.start();
//        trainsMovement.start();
//        alerts.start();
//        watek2.start();










//thread with runable
    }

}
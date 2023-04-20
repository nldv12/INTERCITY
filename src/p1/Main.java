package p1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main");
        RailSystem railSystem = RailSystem.getRailSystem();
        railSystem.defaultRailSystemFill(25);

//        railSystem.alerts.add("Alert 1");
//        railSystem.alerts.add("Alert 2");

        Task_LocomotiveSpeedChange task_LocomotiveSpeedChange = new Task_LocomotiveSpeedChange();
        Task_CLI task_cli = new Task_CLI();
        Task_TrainsMovement task_trainsMovement = new Task_TrainsMovement();
        Task_AppState taskAppState = new Task_AppState();

        Thread locomotiveSpeedChange = new Thread(task_LocomotiveSpeedChange);
        Thread cli = new Thread(task_cli);
        Thread trainsMovement = new Thread(task_trainsMovement);
        Thread appState = new Thread(taskAppState);

        locomotiveSpeedChange.start();
        cli.start();
        trainsMovement.start();
        appState.start();

    }

}
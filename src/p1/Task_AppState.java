package p1;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task_AppState implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();

    @Override
    public void run() {
        String plik = "E:\\Mój dysk\\JAVA\\___GUI___\\__PROJECTS__\\src\\p1\\AppState.txt";



        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            int counter = 0;
            clearFile(plik);
            while (true) {
                if (counter == 10) {
                    clearFile(plik);
                    counter = 0;
                }
                counter++;

                fos = new FileOutputStream(plik, true); // Otwarcie pliku w trybie dopisywania (append)
                pw = new PrintWriter(fos);

                List<Train> sortedTrains = new LinkedList<>(railSystem.getTrainsValues());
                sortedTrains.sort(Comparator.comparingDouble(train -> railSystem.getLocomotive(train.getLocomotiveName()).getDistancePassedPercantageTotal()));

                List<String> listedTrainsKeys = new LinkedList<>();
                for (Train train : sortedTrains) {
                    listedTrainsKeys.add(train.getKey());
                }

                for (String train : listedTrainsKeys) {

                    String locomotive = railSystem.getTrain(train).getLocomotiveName();
                    boolean isMoving = railSystem.getLocomotive(locomotive).isMoving();
                    int carsCount = railSystem.getTrain(train).carsNames.size();
//        double distancePassedPercantageTotal = railSystem.getLocomotive(locomotive).getDistancePassedPercantageTotal();
                    int distancePassedPercantageTotal = (int) railSystem.getLocomotive(locomotive).getDistancePassedPercantageTotal();
//        double distancePassedPercantageLocal = railSystem.getLocomotive(locomotive).getDistancePassedPercantageLocal();
                    int distancePassedPercantageLocal = (int) railSystem.getLocomotive(locomotive).getDistancePassedPercantageLocal();
                    String homeStationName = railSystem.getTrain(train).getHomeStationName();
                    List<String> carNames = new LinkedList<>(railSystem.getCarsKeysSortedByCarWeight(train));

//                pw.println("\n");
                    pw.println("Pociąg: " + train);
                    pw.println("Pociąg pokonał: " + distancePassedPercantageTotal + " procent całej trasy");
                    pw.println("Pociąg pokonał: " + distancePassedPercantageLocal + " procent konkrektnego połączenia");
                    pw.println("Ilość wagonów: " + carsCount);
                    pw.println("Wagony:");
                    for (String carName : carNames) {
                        pw.println(carName);
                    }
                    if (isMoving) {
                        String line = railSystem.getLocomotive(locomotive).currentLineKey.orElse("");
                        String[] cities = line.split("_");
                        pw.println("Pociąg jedize do stacji: " + cities[1]);
                    } else {
                        String station = String.valueOf(railSystem.getLocomotive(locomotive).currentStation);
                        pw.println("Pociąg stoi na stacji: " + station);
                    }
                    pw.println("Lokomotywa: " + locomotive);
                    pw.println("Stacja macierzysta: " + homeStationName);
                }
//                System.out.println("Raporty zapisane w pliku");
                Thread.sleep(5000);
                pw.flush();
                pw.close();
                fos.close();
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas zapisu do pliku: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (pw != null)
                pw.close();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void clearFile(String filePath) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, false);
            fileWriter.write(""); // Zapisujemy pusty napis, co skutkuje usunięciem zawartości pliku
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

}
package p1;

import java.io.*;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Task_AppState implements Runnable {
    RailSystem railSystem = RailSystem.getRailSystem();

    @Override
    public void run() {

        String plik = "E:\\Mój dysk\\JAVA\\___GUI___\\__PROJECTS__\\src\\p1\\AppState.txt";

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            clearFile(plik);
            while (true) {
//                clearFile(plik);
                fos = new FileOutputStream(plik, true); // Otwarcie pliku w trybie dopisywania (append)
                pw = new PrintWriter(fos);

                for (String train : railSystem.trains.keySet()) {

                    String locomotive = railSystem.trains.get(train).getLocomotiveName();
                    boolean isMoving = railSystem.locomotives.get(locomotive).isMoving();
                    int carsCount = railSystem.trains.get(train).carsNames.size();
//        double distancePassedPercantageTotal = railSystem.locomotives.get(locomotive).getDistancePassedPercantageTotal();
                    int distancePassedPercantageTotal = (int) railSystem.locomotives.get(locomotive).getDistancePassedPercantageTotal();
//        double distancePassedPercantageLocal = railSystem.locomotives.get(locomotive).getDistancePassedPercantageLocal();
                    int distancePassedPercantageLocal = (int) railSystem.locomotives.get(locomotive).getDistancePassedPercantageLocal();
                    String homeStationName = railSystem.trains.get(train).getHomeStationName();
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
                        String line = railSystem.locomotives.get(locomotive).currentLineKey.orElse("");
                        String[] cities = line.split("_");
                        pw.println("Pociąg jedize do stacji: " + cities[1]);
                    } else {
                        String station = String.valueOf(railSystem.locomotives.get(locomotive).currentStation);
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
            if (fos != null) {
                try {
                    fos.close();
                    pw.close();
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
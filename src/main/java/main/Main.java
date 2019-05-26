package main;

import http.BexHttpServer;
import models.Airport;
import dijkstra.BuildFlightNetwork;
import models.FlightNetwork;
import dijkstra.ShortestPath;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        Thread httpServer = new Thread(new BexHttpServer());
        httpServer.start();

        try {
            if (!args[0].endsWith(".csv")) {
                System.out.println("The provided file is not valid,you must to provide a valid .csv");
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You must to provide .csv file before running the application: $ bexcolar.com-<SNAPSHOT-VERSION>.jar /absolute/path/to/your/file.csv");
            return;
        }

        BuildFlightNetwork buildNetwork = new BuildFlightNetwork(args[0]);
        if (!buildNetwork.readCsvFile()) {
            System.out.println("An error occurred reading the provided .csv file");
            return;
        }
        while (true) {
            System.out.print("Please enter the route: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            Airport src;
            Airport dest;
            try {
                boolean ctrl = true;
                String[] values = input.split("-");
                src = FlightNetwork.getAirport(values[0]);
                dest = FlightNetwork.getAirport(values[1]);
                if (src == null) {
                    ctrl = false;
                    System.out.println("Source airport not found:" + values[0]);
                } else if (dest == null) {
                    ctrl = false;
                    System.out.println("Destination airport not found:" + values[1]);
                }
                if (ctrl) {
                    ShortestPath.calculateShortestPathFromSource(src);
                    System.out.println("Best route: " + ShortestPath.returnRouteToDestintion(dest));
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Expected: XXX-YYY");
            }
            System.out.println();
        }


    }

}
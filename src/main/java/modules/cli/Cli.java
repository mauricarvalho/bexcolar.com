package modules.cli;

import dijkstra.BexShortestPath;
import models.Airport;
import models.FlightNetwork;

import java.util.Scanner;

import static utils.Constants.*;
import static utils.Constants.INVALID_CLI_INPUT;

public class Cli {

    public static void startCli() {
        while (true) {
            System.out.print(TYPE_ROUTE);
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
                    System.out.println(SRC_NOT_FOUND + values[0]);
                } else if (dest == null) {
                    ctrl = false;
                    System.out.println(DST_NOT_FOUND + values[1]);
                }
                if (ctrl) {
                    BexShortestPath.calculateShortestPathFromSource(src);
                    String path = BexShortestPath.returnRouteToDestintion(dest);
                    if(path.equals("")){
                        System.out.println(NO_ROUTE + src + "-" + dest);
                    } else {}
                    System.out.println(BEST_ROUTE + path);
                }
            } catch (Exception e) {
                System.out.println(INVALID_CLI_INPUT);
            }
            System.out.println();
        }
    }
}

package dijkstra;

import models.Airport;
import models.FlightNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BuildFlightNetwork {


    String path;

    public BuildFlightNetwork(String path) {
        this.path = path;
    }

    public boolean readCsvFile() {

        try {

            BufferedReader br = new BufferedReader(new FileReader(new File(this.path)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                addRoute(values[0], values[1], values[2]);
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void addRoute(String _src, String _dst, String weight) {
        Airport src = FlightNetwork.getAirport(_src) == null ? new Airport(_src) : FlightNetwork.getAirport(_src);
        Airport dest = FlightNetwork.getAirport(_dst) == null ? new Airport(_dst) : FlightNetwork.getAirport(_dst);

        FlightNetwork.addAirport(src);
        FlightNetwork.addAirport(dest);

        if (isNumeric(weight))
            src.addDestination(dest, Integer.parseInt(weight));
    }

    private static boolean  isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}

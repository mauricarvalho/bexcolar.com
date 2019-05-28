package utils;

import models.Airport;
import models.FlightNetwork;

public class Commons {

    public static void addRoute(String _src, String _dst, String weight) {

        Airport src = FlightNetwork.getAirport(_src) == null ? new Airport(_src) : FlightNetwork.getAirport(_src);
        Airport dest = FlightNetwork.getAirport(_dst) == null ? new Airport(_dst) : FlightNetwork.getAirport(_dst);

        FlightNetwork.addAirport(src);
        FlightNetwork.addAirport(dest);

        if (isNumeric(weight))
            src.addDestination(dest, Integer.parseInt(weight));
    }

    private static boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

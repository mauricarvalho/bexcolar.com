package transport;

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
                Airport src = FlightNetwork.getAirport(values[0]) == null ? new Airport(values[0]) : FlightNetwork.getAirport(values[0]);
                Airport dest = FlightNetwork.getAirport(values[1]) == null ? new Airport(values[1]) : FlightNetwork.getAirport(values[1]);

                FlightNetwork.addAirport(src);
                FlightNetwork.addAirport(dest);

                if (isNumeric(values[2]))
                    src.addDestination(dest, Integer.parseInt(values[2]));

            }

        } catch (Exception e) {
            return false;
        }
        System.out.println(FlightNetwork.getAirports());
        return true;
    }

    private boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}

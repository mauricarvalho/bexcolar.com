package utils;

public class Constants {

    //REST

    public static final String INVALID_REST_INPUT = "Invalid query parameters. Expected: /api/bex/route?src=XXX&dst=YYY!";
    public static final String EXPECTED_FORMAT = "Expected format:\n{\n  \"src\": 'XXX',\n  \"dst\": 'YYY',\n  \"weight\":  N\n}";
    public static final String ROUTE_ADDED = "Route added sucessfly. From ";
    public static final String ERROR_CONNECTION = "Error establishing connection with the HTTP Server";
    public static final String API = "/api/bex/route";
    public static final Integer port = 8084;


    //CLI

    public static final String TYPE_ROUTE = "Please enter the route: ";
    public static final String SRC_NOT_FOUND = "Source airport not found:";
    public static final String DST_NOT_FOUND = "Destination airport not found:";
    public static final String INVALID_CLI_INPUT = "Invalid input. Expected: XXX-YYY";
    public static final String INVALID_FILE = "The provided file is not valid,you must to provide a valid .csv";
    public static final String INVALID_ARGUMENTS = "You must to provide .csv file before running the application: $ bexcolar.com-<SNAPSHOT-VERSION>.jar /absolute/path/to/your/file.csv";
    public static final String CSV_ERROR = "An error occurred reading the provided .csv file";


    //COMMON

    public static final String BEST_ROUTE = "Best route: ";
    public static final String NO_ROUTE = "There is no route available between:";

}

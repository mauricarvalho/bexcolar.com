package models;

import models.Airport;

import java.util.HashSet;
import java.util.Set;

public class FlightNetwork {


    private static Set<Airport> airports = new HashSet<>();

    public static void addAirport(Airport a){
        airports.add(a);
    }

    public static Airport getAirport(String airportName){
        for(Airport a : airports){
            if (a.getName().equals(airportName))
                return a;
        }
        return null;
    }


    public static Set<Airport> getAirports() {
        return airports;
    }


}

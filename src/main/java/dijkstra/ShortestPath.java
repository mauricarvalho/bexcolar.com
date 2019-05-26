package dijkstra;

import models.Airport;
import models.FlightNetwork;

import java.util.*;

public class ShortestPath {


    public static Set<Airport> calculateShortestPathFromSource(Airport source) {
        source.setDistance(0);

        Set<Airport> visited = new HashSet<>();
        Set<Airport> unvisited = new HashSet<>();

        unvisited.add(source);

        while (unvisited.size() != 0) {
            Airport current = getLowestDistance(unvisited);
            unvisited.remove(current);
            for (Map.Entry<Airport, Integer> adjacencyPair :
                    current.getNeighbors().entrySet()) {
                Airport adj = adjacencyPair.getKey();
                Integer weight = adjacencyPair.getValue();
                if (!visited.contains(adj)) {
                    calculateMinimumDistance(adj, weight, current);
                    unvisited.add(adj);
                }
            }
            visited.add(current);
        }
        return FlightNetwork.getAirports();
    }

    private static Airport getLowestDistance(Set<Airport> unvisited) {
        Airport lowestDist = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Airport airport : unvisited) {
            int airDist = airport.getDistance();
            if (airDist < lowestDistance) {
                lowestDistance = airDist;
                lowestDist = airport;
            }
        }
        return lowestDist;
    }


    private static void calculateMinimumDistance(Airport evaluation,
                                                 Integer weigh, Airport sourceAirport) {
        Integer sourceDistance = sourceAirport.getDistance();
        if (sourceDistance + weigh < evaluation.getDistance()) {
            evaluation.setDistance(sourceDistance + weigh);
            LinkedList<Airport> shortestPath = new LinkedList<>(sourceAirport.getShortestPath());
            shortestPath.add(sourceAirport);
            evaluation.setShortestPath(shortestPath);
        }
    }

    public static String returnRouteToDestintion(Airport dest){
        StringBuilder route = new StringBuilder();
        for(Airport d : FlightNetwork.getAirports()){
            if(d.equals(dest)){
                LinkedList<Airport> ll = d.getShortestPath();
                route.append(ll.getFirst().getName());
                ll.removeFirst();
                while(!ll.isEmpty()){
                    route.append(" - " + ll.getFirst().getName());
                    ll.removeFirst();
                }
                route.append(" - " + dest.getName() +" > " + d.getDistance());
            }
        }
        return route.toString();
    }

}

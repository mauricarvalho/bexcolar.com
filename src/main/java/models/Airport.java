package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Airport {

    private String name;

    private LinkedList<Airport> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Airport, Integer> neighbors = new HashMap<>();

    public Airport(String name){
        this.name = name;
    }

    public void addDestination(Airport dest, int dist){
        neighbors.put(dest,dist);
    }

    public LinkedList<Airport> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(LinkedList<Airport> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Airport, Integer> getNeighbors() {
        return neighbors;
    }

    public String getName(){
        return this.name;
    }
}

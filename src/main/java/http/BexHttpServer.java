package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dijkstra.BuildFlightNetwork;
import dijkstra.ShortestPath;
import models.Airport;
import models.FlightNetwork;
import models.Route;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class BexHttpServer implements Runnable {
    @Override
    public void run() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8084), 0);
        } catch (IOException e) {
            System.out.println("Error establishing connection with the HTTP Server");
        }
        server.createContext("/api/bex/route", (request -> {

            if ("GET".equals(request.getRequestMethod())) {
                String src = parseQuery(request.getRequestURI().getRawQuery(), 0);
                String dst = parseQuery(request.getRequestURI().getRawQuery(), 1);
                if (src == null || dst == null) {
                    String respText = "Invalid query parameters. Expected: /api/bex/route?src=XXX&dst=YYY!";
                    request.sendResponseHeaders(400, respText.getBytes().length);
                    OutputStream output = request.getResponseBody();
                    output.write(respText.getBytes());
                    output.flush();
                } else {
                    Airport _src = FlightNetwork.getAirport(src);
                    Airport _dest = FlightNetwork.getAirport(dst);
                    ShortestPath.calculateShortestPathFromSource(_src);
                    String respText = "Best route: " + ShortestPath.returnRouteToDestintion(_dest);
                    request.sendResponseHeaders(200, respText.getBytes().length);
                    OutputStream output = request.getResponseBody();
                    output.write(respText.getBytes());
                    output.flush();
                }

            } else if ("POST".equals(request.getRequestMethod())) {
                Route route = parseBody(request.getRequestBody());
                if (route == null) {
                    StringBuilder respText = new StringBuilder();
                    respText.append("Expected format:\n");
                    respText.append("{\n");
                    respText.append("  \"src\": 'XXX',\n");
                    respText.append("  \"dst\": 'YYY',\n");
                    respText.append("  \"weight\":  N\n");
                    respText.append("}");
                    request.sendResponseHeaders(400, respText.toString().getBytes().length);
                    OutputStream output = request.getResponseBody();
                    output.write(respText.toString().getBytes());
                    output.flush();
                } else {

                    String respText = "Route added sucessfly. From " + route.getSrc() + " to " + route.getDst() + " > $" + route.getWeight();
                    request.sendResponseHeaders(200, respText.getBytes().length);
                    OutputStream output = request.getResponseBody();
                    output.write(respText.getBytes());
                    output.flush();
                }
            }
            request.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private Route parseBody(InputStream responseBody) {
        try {
            Route route = convertRoute(responseBody);
            BuildFlightNetwork.addRoute(route.getSrc(), route.getDst(), route.getWeight().toString());
            return route;

        } catch (Exception e) {
            return null;
        }
    }

    private String parseQuery(String query, Integer index) {
        try {
            String[] param = query.split("&");
            String param1 = param[index].split("=")[0];
            String param2 = param[index].split("=")[1];
            if (param1.equals("src") || param1.equals("dst"))
                return param2;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private static Route convertRoute(InputStream is) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(is, Route.class);
    }

}

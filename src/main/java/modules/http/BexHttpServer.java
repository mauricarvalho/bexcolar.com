package modules.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dijkstra.BexShortestPath;
import models.Airport;
import models.FlightNetwork;
import models.Route;
import utils.Commons;
import utils.FileOperations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import static utils.Constants.*;

public class BexHttpServer implements Runnable {

    public static void startHttpServer() {
        Thread httpServer = new Thread(new BexHttpServer());
        httpServer.start();
    }

    @Override
    public void run() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            System.out.println(ERROR_CONNECTION);
        }
        server.createContext(API, (request -> {
            controller(request);
            request.close();
        }));

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void controller(HttpExchange request) throws IOException {
        if ("GET".equals(request.getRequestMethod())) {
            String src = parseQuery(request.getRequestURI().getRawQuery(), 0);
            String dst = parseQuery(request.getRequestURI().getRawQuery(), 1);
            if (src == null || dst == null) {
                buildHttpResponse(request, HttpURLConnection.HTTP_BAD_REQUEST, INVALID_REST_INPUT);
            } else {
                Airport _src = FlightNetwork.getAirport(src);
                Airport _dest = FlightNetwork.getAirport(dst);
                BexShortestPath.calculateShortestPathFromSource(_src);
                String path = BexShortestPath.returnRouteToDestintion(_dest);
                buildHttpResponse(request, HttpURLConnection.HTTP_OK, buildSuccessResponse(src, dst, path));
            }
        } else if ("POST".equals(request.getRequestMethod())) {
            Route route = addRoute(request.getRequestBody());
            if (route == null) {
                buildHttpResponse(request, HttpURLConnection.HTTP_BAD_REQUEST, EXPECTED_FORMAT);
            } else {
                buildHttpResponse(request, HttpURLConnection.HTTP_OK, buildAddedRouteResponse(route));
            }
        }
    }


    //PRIVATE METHODS

    private String buildAddedRouteResponse(Route route) {
        return ROUTE_ADDED + route.getSrc() + " to " + route.getDst() + " > $" + route.getWeight();
    }

    private String buildSuccessResponse(String src, String dst, String path) {
        String respText;
        if (path != null && !path.isEmpty()) {
            respText = BEST_ROUTE + path;
        } else {
            respText = NO_ROUTE + src + "-" + dst;
        }
        return respText;
    }

    private void buildHttpResponse(HttpExchange request, int statusCode, String result) throws IOException {
        request.sendResponseHeaders(statusCode, result.getBytes().length);
        OutputStream output = request.getResponseBody();
        output.write(result.getBytes());
        output.flush();
    }

    private Route addRoute(InputStream responseBody) {
        try {
            Route route = convertRoute(responseBody);
            if (FileOperations.addToCsv(route.getSrc(), route.getDst(), route.getWeight().toString())) {
                Commons.addRoute(route.getSrc(), route.getDst(), route.getWeight().toString());
                return route;
            }
            return null;
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

    private Route convertRoute(InputStream is) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(is, Route.class);
    }

}

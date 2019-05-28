package main;

import modules.cli.Cli;
import dijkstra.BexShortestPath;
import modules.http.BexHttpServer;

public class Main {

    public static void main(String[] args) {

        if (!BexShortestPath.buildNetwork(args[0])) return;

        BexHttpServer.startHttpServer();

        Cli.startCli();
    }

}
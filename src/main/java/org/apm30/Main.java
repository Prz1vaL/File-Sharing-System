package org.apm30;

import org.apm30.client.Client;
import org.apm30.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        // Create a client object and start it
        Client client = new Client();
        client.start();

        // Create a server object and start it
        Server server = new Server();
        server.start();
    }
}
package com.example.chens.PlaymySong.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class SetupServer {
    /**
     * @param args
     */
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(3000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 3000.");
            System.exit(1);
        }

        String strLocalHost = "";
        try{
            strLocalHost = InetAddress.getLocalHost().getHostName();
            System.out.println("Host name: " + strLocalHost);
        } catch (UnknownHostException e){
            System.err.println ("Unable to find local host");
        }

        DefaultSocketClient clientSocket = new DefaultSocketClient();
        clientSocket.init();
        while (true) {
            // start working
            clientSocket = new DefaultSocketClient();

            try {
                clientSocket.setSocket(serverSocket.accept());
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            clientSocket.start();
        }
    }
}

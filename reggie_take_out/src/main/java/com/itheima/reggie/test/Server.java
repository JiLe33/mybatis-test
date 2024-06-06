package com.itheima.reggie.test;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server started. Waiting for clients...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String inputLine;
            String outputLine;

            while (true) {
                if ((inputLine = in.readLine()) != null) {
                    System.out.println("Client: " + inputLine);
                }

                outputLine = consoleInput.readLine();
                out.println(outputLine);

                if (outputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.itheima.reggie.test;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String inputLine;
            String outputLine;

            while (true) {
                outputLine = consoleInput.readLine();
                out.println(outputLine);

                if ((inputLine = in.readLine()) != null) {
                    System.out.println("Server: " + inputLine);
                }

                if (outputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
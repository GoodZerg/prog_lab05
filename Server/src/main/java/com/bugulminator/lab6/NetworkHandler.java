package com.bugulminator.lab6;

import com.bugulminator.lab6.network.C2SPackage;
import com.bugulminator.lab6.network.PayloadHandler;
import com.bugulminator.lab6.network.S2CPackage;
import com.bugulminator.lab6.command.Invoker;

import java.io.*;
import java.net.*;

public class NetworkHandler {
    private static NetworkHandler instance = null;
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final String DEFAULT_SERVER_PORT = "54321";
    public static final int BUFFER_SIZE = 0xFFFF;

    private final ServerSocket serverSocket;

    private NetworkHandler() throws IOException {
        String serverAddress = System.getenv("LAB_SERVER_ADDRESS");
        String rawServerPort = System.getenv("LAB_SERVER_PORT");

        serverAddress = serverAddress != null ? serverAddress : DEFAULT_SERVER_ADDRESS;
        rawServerPort = rawServerPort != null ? rawServerPort : DEFAULT_SERVER_PORT;

        int serverPort;

        final int MIN_PORT = 0x0000, MAX_PORT = 0xFFFF;

        try {
            serverPort = Integer.parseInt(rawServerPort);
            if (serverPort < MIN_PORT || MAX_PORT < serverPort) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ignore) {
            System.err.println("Incorrect value provided for $LAB_CLIENT_PORT, using default(" + DEFAULT_SERVER_PORT + ")");
            serverPort = Integer.parseInt(DEFAULT_SERVER_PORT);
        }

        serverSocket = new ServerSocket(serverPort, 50, InetAddress.getByName(serverAddress));
        System.out.println("Server is ready to accept connections");
    }

    public void start() {
        while (true) {
            try {
                acceptConnections();
            } catch (IOException ex) {
                System.err.println("Error while processing connection: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void acceptConnections() throws IOException {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connection accepted: " + clientSocket.getRemoteSocketAddress());

        try (
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
        ) {
            while (true) {
                Thread.onSpinWait();
                try {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    inputStream.read(buffer);
                    C2SPackage request = (C2SPackage) PayloadHandler.deserialize(buffer);
                    System.out.println(request.clazz());
                    Invoker.getInstance().processRemoteRequest(request, outputStream);
                } catch (IOException ex) {
                    System.out.println("Connection closed: " + clientSocket.getRemoteSocketAddress());
                    break;
                } catch (ClassNotFoundException ex) {
                    System.err.println("Received broken package");
                }
            }
        } finally {
            clientSocket.close();
        }
    }

    public void sendPackage(S2CPackage data, OutputStream outputStream) {
        try {
            byte[] buffer = PayloadHandler.serialize(data);
            outputStream.write(buffer, 0, buffer.length);
            System.out.println("Sending message to client");
        } catch (IOException ex) {
            System.err.println("Unable to send package");
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public static NetworkHandler getInstance() {
        if (instance == null) {
            try {
                instance = new NetworkHandler();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return instance;
    }
}

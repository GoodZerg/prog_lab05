package com.bugulminator.lab6;

import com.bugulminator.lab6.network.C2SPackage;
import com.bugulminator.lab6.network.PayloadHandler;
import com.bugulminator.lab6.network.S2CPackage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class NetworkHandler {
    private static NetworkHandler instance = null;

    public static final String DEFAULT_ADDRESS = "localhost";
    public static final String DEFAULT_CLIENT_PORT = Integer.toString(30000 + (int)(4000.0 * Math.random()));
    public static final String DEFAULT_SERVER_PORT = "54321";
    public static final int BUFFER_SIZE = 0xFFFF;
    public static final String TRUE_RESPONSE = "true";
    public static final String FALSE_RESPONSE = "false";
    private static String lastResponse = null;

    private final Selector selector;
    private SocketChannel socketChannel = null;

    private NetworkHandler() throws IOException {
        String clientAddress = System.getenv("LAB_CLIENT_ADDRESS");
        String rawClientPort = System.getenv("LAB_CLIENT_PORT");
        String serverAddress = System.getenv("LAB_SERVER_ADDRESS");
        String rawServerPort = System.getenv("LAB_SERVER_PORT");

        clientAddress = clientAddress != null ? clientAddress : DEFAULT_ADDRESS;
        serverAddress = serverAddress != null ? serverAddress : DEFAULT_ADDRESS;
        rawClientPort = rawClientPort != null ? rawClientPort : DEFAULT_CLIENT_PORT;
        rawServerPort = rawServerPort != null ? rawServerPort : DEFAULT_SERVER_PORT;

        int clientPort, serverPort;

        final int MIN_PORT = 0x0000, MAX_PORT = 0xFFFF;

        try {
            clientPort = Integer.parseInt(rawClientPort);
            if (clientPort < MIN_PORT || MAX_PORT < clientPort) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ignore) {
            System.err.println("Incorrect value provided for $LAB_CLIENT_PORT, using default(" + DEFAULT_CLIENT_PORT + ")");
            clientPort = Integer.parseInt(DEFAULT_CLIENT_PORT);
        }
        try {
            serverPort = Integer.parseInt(rawServerPort);
            if (serverPort < MIN_PORT || MAX_PORT < serverPort) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ignore) {
            System.err.println("Incorrect value provided for $LAB_CLIENT_PORT, using default(" + DEFAULT_SERVER_PORT + ")");
            serverPort = Integer.parseInt(DEFAULT_SERVER_PORT);
        }

        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.bind(new InetSocketAddress(clientAddress, clientPort));
        socketChannel.connect(new InetSocketAddress(serverAddress, serverPort));
        socketChannel.configureBlocking(false);

        int operations = socketChannel.validOps();
        socketChannel.register(selector, operations, null);

        System.out.println("Client is running on " + clientAddress + ":" + clientPort);
    }

    public void start() {

        while (true) {
            Thread.onSpinWait();
            try {
                acceptConnections();
            } catch (IOException ex) {
                System.err.println("Error while processing connection:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void acceptConnections() throws IOException {
        selector.select();

        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> selectionKeyIterator = keys.iterator();

        while (selectionKeyIterator.hasNext()) {
            SelectionKey currentKey = selectionKeyIterator.next();

            if (currentKey.isReadable()) {
                SocketChannel client = (SocketChannel) currentKey.channel();

                try {
                    S2CPackage response = receivePackage(client);
                    lastResponse = response.response();
                    if (!Objects.equals(lastResponse, TRUE_RESPONSE)
                            && !Objects.equals(lastResponse, FALSE_RESPONSE)) {
                        System.out.println(response.response());
                    }
                } catch (SocketException ex) {
                    System.out.println("Connection with server was aborted");
                    client.close();
                    this.close();
                    System.exit(0);
                } catch (ClassNotFoundException ex) {
                    System.err.println("Received broken package");
                }
            }

            selectionKeyIterator.remove();
        }
    }

    private void sendBytes(byte[] bytes) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            socketChannel.write(buffer);
            System.out.println("Sending message to server");
            buffer.clear();
        } catch (IOException ex) {
            System.err.println("Error while sending package to server");
        }
    }

    private byte[] receiveBytes(SocketChannel remote) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        remote.read(buffer);
        return buffer.array();
    }

    public void sendPackage(C2SPackage data) {
        lastResponse = null;
        try {
            byte[] bytes = PayloadHandler.serialize(data);
            sendBytes(bytes);
        } catch (IOException ex) {
            System.err.println("Unable to send package");
        }
    }

    public S2CPackage receivePackage(SocketChannel remote) throws IOException, ClassNotFoundException {
        byte[] bytes = receiveBytes(remote);
        return (S2CPackage) PayloadHandler.deserialize(bytes);
    }

    public void close() throws IOException {
        socketChannel.close();
    }

    public static NetworkHandler getInstance() {
        if (instance == null) {
            try {
                instance = new NetworkHandler();
            } catch (IOException ex) {
                System.err.println("Server is unreachable, trying again in 5s");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return instance;
    }

    public String getLastResponse() {
        return lastResponse;
    }
}

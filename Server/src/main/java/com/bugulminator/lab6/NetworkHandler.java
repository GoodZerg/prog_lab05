package com.bugulminator.lab6;

import com.bugulminator.lab6.collection.DatabaseManager;
import com.bugulminator.lab6.commands.CommandRegister;
import com.bugulminator.lab6.network.C2SPackage;
import com.bugulminator.lab6.network.PayloadHandler;
import com.bugulminator.lab6.network.ResponseStatus;
import com.bugulminator.lab6.network.S2CPackage;
import com.bugulminator.lab6.command.Invoker;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class NetworkHandler {
    private static NetworkHandler instance = null;
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final String DEFAULT_SERVER_PORT = "54321";
    public static final int BUFFER_SIZE = 0xFFFF;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(6);
    private final ForkJoinPool forkJoinPool2 = new ForkJoinPool(6);

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

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

        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverAddress, serverPort));
        serverSocketChannel.configureBlocking(false);


        int operations = serverSocketChannel.validOps();
        serverSocketChannel.register(selector, operations, null);

        System.out.println("Server is ready to accept connections");
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

            if (currentKey.isAcceptable()) {
                SocketChannel client = serverSocketChannel.accept();
                client.configureBlocking(false);

                client.register(selector, SelectionKey.OP_READ);
                System.out.println("Connection accepted: " + client.getRemoteAddress());
            } else if (currentKey.isReadable()) {
                SocketChannel client = (SocketChannel) currentKey.channel();

                forkJoinPool.submit(() -> {
                    try {
                        C2SPackage request = receivePackage(client);
                        if (!DatabaseManager.auth(request.credentials().login(), request.credentials().password())
                                && request.clazz() != CommandRegister.class) {
                            NetworkHandler.getInstance().sendPackage(
                                    new S2CPackage("Incorrect credentials", ResponseStatus.ERROR),
                                    client
                            );
                        } else {
                            new Thread(() -> {
                                Invoker.getInstance().processRemoteRequest(request, client);
                            }).start();
                        }
                    } catch (SocketException ex) {
                        try {
                            System.out.println("Connection closed: " + client.getRemoteAddress());
                            client.close();
                        } catch (IOException ignore) {}

                    } catch (ClassNotFoundException ex) {
                        System.err.println("Received broken package");
                    } catch (IOException ex) {
                        System.err.println("Error while processing connection:" + ex.getMessage());
                        ex.printStackTrace();
                    }
                });
            }

            selectionKeyIterator.remove();
        }
    }

    public void sendPackage(S2CPackage data, SocketChannel remote) {
        forkJoinPool2.submit(() -> {
            try {
                byte[] bytes = PayloadHandler.serialize(data);
                sendBytes(bytes, remote);
            } catch (IOException ex) {
                System.err.println("Unable to send package");
            }
        });
    }

    public void sendBytes(byte[] bytes, SocketChannel client) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            client.write(buffer);
            System.out.println("Sending message to " + client.getRemoteAddress());
            buffer.clear();
        } catch (IOException ex) {
            System.err.println("Error while sending package to client");
        }
    }

    public byte[] receiveBytes(SocketChannel remote) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        remote.read(buffer);
        return buffer.array();
    }

    public C2SPackage receivePackage(SocketChannel remote) throws IOException, ClassNotFoundException {
        System.out.println("Received package from " + remote.getRemoteAddress());
        byte[] bytes = receiveBytes(remote);
        return (C2SPackage) PayloadHandler.deserialize(bytes);
    }

    public void close() throws IOException {
        serverSocketChannel.close();
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

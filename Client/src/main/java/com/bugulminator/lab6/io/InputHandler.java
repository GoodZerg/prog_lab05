package com.bugulminator.lab6.io;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

/**
 * The type Input handler.
 */
public class InputHandler {
    private final DeqCollection<?> data;
    private final Invoker parser;

    /**
     * Instantiates a new Input handler.
     *
     * @param data the data
     */
    public InputHandler(DeqCollection<?> data) {
        this.data = data;
        this.parser = new Invoker(data);
    }

    /**
     * Start.
     *
     * @param fileName the file name
     */
    public void start(String fileName) {
        data.load(new FileReader(fileName));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                try {
                    parser.parseCommand(reader.readLine(), reader, true);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error in reading command");
        }
    }

}

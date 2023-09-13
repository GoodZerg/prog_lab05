package com.bugulminator.lab6.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type Output handler.
 */
public class OutputHandler {
    /**
     * The File.
     */
    String file;
    /**
     * The Write.
     */
    BufferedOutputStream write;

    /**
     * Instantiates a new Output handler.
     *
     * @param str the str
     */
    public OutputHandler(String str) {
        file = str;
    }

    /**
     * Write line.
     *
     * @param str the str
     */
    public void writeLine(String str) {
        try {
            write.write(str.getBytes(), 0, str.getBytes().length);
        } catch (IOException e) {
            System.err.println("Cannot write");
        }
    }

    /**
     * Start.
     */
    public void start() {
        try {
            this.write = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            System.err.println("Cannot write");
        }
    }

    /**
     * Close.
     */
    public void close() {
        try {
            this.write.close();
        } catch (IOException e) {
            System.err.println("Cannot write");
        }
    }
}

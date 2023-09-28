package com.bugulminator.lab6.io;

import java.io.*;

/**
 * The type File reader.
 */
public class FileReader {
    private final BufferedReader reader;

    /**
     * Instantiates a new File reader.
     *
     * @param file_name the file name
     */
    FileReader(String file_name) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_name)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Get string.
     *
     * @return the string
     */
    public String get() {
        try {
            return reader.readLine();
        } catch (IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
    }

    /**
     * Ready boolean.
     *
     * @return the boolean
     */
    public boolean ready() {
        try {
            return reader.ready();
        } catch (IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
    }

    /**
     * Close.
     */
    public void close() {
        try {
            reader.close();
        } catch (IOException exception) {
            throw new RuntimeException
                    ("reading Error\njava.io.IOException:::::" +
                            exception.getMessage());
        }
    }
}

package com.bugulminator.lab6.collection;

import java.io.BufferedReader;

/**
 * The interface main.lab6.Collectible.
 */
public interface Collectible {
    /**
     * Load from csv.
     *
     * @param str the str
     */
    void loadFromCsv(String str);

    /**
     * Load from standard input.
     *
     * @param _reader         the reader
     * @param isStandardInput the is standard input
     */
    void loadFromStandardInput(BufferedReader _reader, boolean isStandardInput);

    /**
     * Convert to csv string.
     *
     * @return the string
     */
    String convertToCsv();

    /**
     * Gets id.
     *
     * @return the id
     */
    long getId();

    /**
     * Gets distance.
     *
     * @return the distance
     */
    Integer getDistance();

    /**
     * Compare by cord int.
     *
     * @param o the o
     * @return the int
     */
    int compareByCord(Object o);

    /**
     * Sets start id.
     *
     * @param id the id
     */
    void setStartID(long id);
}

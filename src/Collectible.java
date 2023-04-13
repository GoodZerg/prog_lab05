import java.io.BufferedReader;

/**
 * The interface Collectible.
 */
public interface Collectible {
    /**
     * Load from csv.
     *
     * @param str the str
     */
    public void loadFromCsv(String str);

    /**
     * Load from standard input.
     *
     * @param _reader         the reader
     * @param isStandardInput the is standard input
     */
    public void loadFromStandardInput(BufferedReader _reader, boolean isStandardInput);

    /**
     * Convert to csv string.
     *
     * @return the string
     */
    public String convertToCsv();

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId();

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public Integer getDistance();

    /**
     * Compare by cord int.
     *
     * @param o the o
     * @return the int
     */
    public int compareByCord(Object o);

    /**
     * Sets start id.
     *
     * @param id the id
     */
    public void setStartID(long id);
}

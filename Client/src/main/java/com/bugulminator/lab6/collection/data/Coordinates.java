package com.bugulminator.lab6.collection.data;

/**
 * The type main.lab6.Coordinates.
 */
public class Coordinates implements Comparable<Coordinates> {
    private long x; //Максимальное значение поля: 211
    private Float y; //Максимальное значение поля: 899, Поле не может быть null

    /**
     * Instantiates a new main.lab6.Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    Coordinates(long x, Float y) {
        setX(x);
        setY(y);
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public long getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(long x) {
        if (x > 211)
            throw new RuntimeException("Error Coordinate:X");
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Float getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Float y) {
        if (y > 899)
            throw new RuntimeException("Error Coordinate:Y");
        this.y = y;
    }

    @Override
    public String toString() {
        return "     X: " +
                getX() +
                "\n" +
                "     Y: " +
                getY();
    }

    @Override
    public int compareTo(Coordinates o) {
        return (int) ((x + y) - (o.getX() + o.getY()));
    }

    /**
     * To csv string.
     *
     * @return the string
     */
    public String toCsv() {
        return getX() + "," + getY();
    }
}

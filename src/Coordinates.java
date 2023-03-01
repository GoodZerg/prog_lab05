public class Coordinates {
    private long x; //Максимальное значение поля: 211
    private Float y; //Максимальное значение поля: 899, Поле не может быть null

    Coordinates(long x, Float y){
        setX(x);
        setY(y);
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        if(x > 211)
            throw new RuntimeException("Error Coordinate");
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        if(y > 899)
            throw new RuntimeException("Error Coordinate");
        this.y = y;
    }

}

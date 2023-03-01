public class Location {
    private Long x; //Поле не может быть null
    private int y;
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        if(x == null)
            throw new RuntimeException("Error Location");
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.equals(""))
            throw new RuntimeException("Error Coordinate");
        this.name = name;
    }
}

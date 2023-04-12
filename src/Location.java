public class Location {
    private Long x; //Поле не может быть null
    private int y;
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Long getX() {
        return x;
    }

    Location(Long x, int y, String name){
        setX(x);
        setY(y);
        setName(name);
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("     X: ");    sb.append(getX());    sb.append("\n");
        sb.append("     Y: ");    sb.append(getY());    sb.append("\n");
        sb.append("     Name: "); sb.append(getName());
        return sb.toString();
    }

    public String toCsv() {
        return getX() + "," + getY() + "," + getName();
    }
}

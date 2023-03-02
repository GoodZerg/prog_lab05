import java.time.LocalDate;

public class Route implements Collectible, Comparable<Route>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле не может быть null
    private Location to; //Поле может быть null
    private Integer distance; //Поле не может быть null, Значение поля должно быть больше 1

    private static long nextId = 0;

    Route(){
        id = ++nextId;
        setCreationDate(java.time.LocalDate.now());
        loadFromStandardInput();
    }

    Route(long id, String name, Coordinates coordinates, java.time.LocalDate creationDate,
          Location from, Location to, Integer distance){
        setId(id);
        setName( name);
        setCoordinates(coordinates);
        setCreationDate(creationDate);
        setFrom(from);
        setTo(to);
        setDistance(distance);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.equals(""))
            throw new RuntimeException("Error Route");
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if(coordinates == null)
            throw new RuntimeException("Error Route");
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        if(creationDate == null)
            throw new RuntimeException("Error Route");
        this.creationDate = creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        if(from == null)
            throw new RuntimeException("Error Route");
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        if (distance == null || distance <= 1)
            throw new RuntimeException("Error Route");
        this.distance = distance;
    }

    @Override
    public void loadFromCsv(String str) {

    }

    @Override
    public void loadFromStandardInput() {

    }

    @Override
    public String convertToCsv() {
        return null;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Route o) {
        return 0;
    }

}

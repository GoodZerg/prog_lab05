import org.jetbrains.annotations.NotNull;

public class Route implements Collectible, Comparable<Route> {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле не может быть null
    private Location to; //Поле может быть null
    private Integer distance; //Поле не может быть null, Значение поля должно быть больше 1


    ////////////////////////////////////////////////TODO
    @Override
    public void loadFromCsv(String str) {

    }

    @Override
    public void loadFromString(String str) {

    }

    @Override
    public String convertToCsv() {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public int compareTo(@NotNull Route o) {
        return 0;
    }
}

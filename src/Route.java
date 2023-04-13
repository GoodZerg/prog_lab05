import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * The type Route.
 */
public class Route implements Collectible, Comparable<Route>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле не может быть null
    private Location to; //Поле может быть null
    private Integer distance; //Поле не может быть null, Значение поля должно быть больше 1

    private static long nextId = 0;

    /**
     * Instantiates a new Route.
     */
    Route(){
        id = ++nextId;
        setCreationDate(java.time.LocalDate.now());
//        loadFromStandardInput();
    }

    /**
     * Instantiates a new Route.
     *
     * @param id           the id
     * @param name         the name
     * @param coordinates  the coordinates
     * @param creationDate the creation date
     * @param from         the from
     * @param to           the to
     * @param distance     the distance
     */
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

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        if(name == null || name.equals(""))
            throw new RuntimeException("Error Route");
        this.name = name;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        if(coordinates == null)
            throw new RuntimeException("Error Route");
        this.coordinates = coordinates;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(LocalDate creationDate) {
        if(creationDate == null)
            throw new RuntimeException("Error Route");
        this.creationDate = creationDate;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public Location getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(Location from) {
        if(from == null)
            throw new RuntimeException("Error Route");
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public Location getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(Location to) {
        this.to = to;
    }

    @Override
    public Integer getDistance() {
        return distance;
    }

    @Override
    public int compareByCord(Object o) {
        return coordinates.compareTo(((Route)o).getCoordinates());
    }

    @Override
    public void setStartID(long id) {
        nextId = id;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(Integer distance) {
        if (distance == null || distance <= 1)
            throw new RuntimeException("Error Route");
        this.distance = distance;
    }

    @Override
    public void loadFromCsv(String str) {
        String[] line = str.split(",");
        long id;
        String name;
        long x;
        Float y;
        java.time.LocalDate creationDate;
        Long x1;
        int y1;
        String name1;
        Long x2;
        int y2;
        String name2;
        Integer distance;
        try {
            id = Long.parseLong(line[0]);
            name = line[1];
            x = Long.parseLong(line[2]);
            y = Float.parseFloat(line[3]);
            creationDate = java.time.LocalDate.parse(line[4]);
            x1 = Long.parseLong(line[5]);
            y1 = Integer.parseInt(line[6]);
            name1 = line[7];
            x2 = Long.parseLong(line[8]);
            y2 = Integer.parseInt(line[9]);
            name2 = line[10];
            distance = Integer.parseInt(line[11]);
        }catch (RuntimeException e){
            throw e;
        }
        setId(id);
        setName(name);
        setCoordinates(new Coordinates(x, y));
        setCreationDate(creationDate);
        setFrom(new Location(x1, y1, name1));
        setTo(new Location(x2, y2, name2));
        setDistance(distance);
    }
    private void printIf(String str, boolean p){
        if(p){
            System.out.println(str);
        }
    }

    private void traceBackExceptionIf(String what, boolean p){
        System.out.println(what);
        if(p){
            throw new RuntimeException("");
        }
    }

    @Override
    public void loadFromStandardInput(BufferedReader _reader, boolean isStandardInput) {
        String name;
        while (true) {
            try {
                printIf("name: ", isStandardInput);
                name = _reader.readLine();
                setName(name);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong name", !isStandardInput);
            }
        }

        printIf("coordinates: ", isStandardInput);
        Coordinates cor = new Coordinates(0L,0F);
        String tmp;
        Long X;
        Float Y;
        while (true) {
            try {
                printIf("X: ", isStandardInput);
                tmp = _reader.readLine();
                X = Long.parseLong(tmp);
                cor.setX(X);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong X", !isStandardInput);
            }
        }
        while (true) {
            try {
                printIf("Y: ", isStandardInput);
                tmp = _reader.readLine();
                Y = Float.parseFloat(tmp);
                cor.setY(Y);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong Y", !isStandardInput);
            }
        }
        setCoordinates(cor);

        printIf("From: ", isStandardInput);
        Location from = new Location(0L,0, "asd");
        String FName;
        Long FX;
        int FY;
        while (true) {
            try {
                printIf("X: ", isStandardInput);
                tmp = _reader.readLine();
                FX = Long.parseLong(tmp);
                from.setX(FX);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong X", !isStandardInput);
            }
        }
        while (true) {
            try {
                printIf("Y: ", isStandardInput);
                tmp = _reader.readLine();
                FY = Integer.parseInt(tmp);
                from.setY(FY);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong Y", !isStandardInput);
            }
        }
        while (true) {
            try {
                printIf("name: ", isStandardInput);
                FName = _reader.readLine();
                from.setName(FName);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong name", !isStandardInput);
            }
        }
        setFrom(from);

        printIf("To: ", isStandardInput);
        Location to = new Location(0L,0, "asd");
        String TName;
        Long TX;
        int TY;
        while (true) {
            try {
                printIf("X: ", isStandardInput);
                tmp = _reader.readLine();
                TX = Long.parseLong(tmp);
                to.setX(TX);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong X", !isStandardInput);
            }
        }
        while (true) {
            try {
                printIf("Y: ", isStandardInput);
                tmp = _reader.readLine();
                TY = Integer.parseInt(tmp);
                to.setY(TY);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong Y", isStandardInput);
            }
        }
        while (true) {
            try {
                printIf("name: ", isStandardInput);
                TName = _reader.readLine();
                to.setName(TName);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                traceBackExceptionIf("wrong name", !isStandardInput);
            }
        }
        setTo(to);
        Integer distance = 0;
        while (true) {
            try {
                printIf("distance: ", isStandardInput);
                tmp = _reader.readLine();
                distance = Integer.parseInt(tmp);
                setDistance(distance);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println(tmp);
                traceBackExceptionIf("wrong distance", !isStandardInput);
            }
        }
    }


    @Override
    public String convertToCsv() {
        return
        getId() + "," +
        getName() + "," +
        getCoordinates().toCsv() + "," +
        getCreationDate() + "," +
        getFrom().toCsv() + "," +
        getTo().toCsv() + "," +
        getDistance().toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("");
        sb.append("Id: ");             sb.append(getId());                      sb.append("\n");
        sb.append("Name: ");           sb.append(getName());                    sb.append("\n");
        sb.append("Coordinates: \n");  sb.append(getCoordinates().toString());  sb.append("\n");
        sb.append("CreationDate: ");   sb.append(getCreationDate().toString()); sb.append("\n");
        sb.append("From: \n");         sb.append(getFrom().toString());         sb.append("\n");
        sb.append("To: \n");           sb.append(getTo().toString());           sb.append("\n");
        sb.append("Distance: ");       sb.append(getDistance());                sb.append("\n");
        return sb.toString();
        //return Long.toString(id);
    }


    @Override
    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Route o) {
        return this.distance - o.distance;
    }

}

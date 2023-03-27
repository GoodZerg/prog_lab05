import java.io.BufferedReader;
import java.io.InputStreamReader;
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
//        loadFromStandardInput();
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name;
        while (true) {
            try {
                System.out.println("name: ");
                name = reader.readLine();
                setName(name);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong name");
            }
        }

        System.out.println("coordinates: ");
        Coordinates cor = new Coordinates(0L,0F);
        String tmp;
        Long X;
        Float Y;
        while (true) {
            try {
                System.out.println("X: ");
                tmp = reader.readLine();
                X = Long.parseLong(tmp);
                cor.setX(X);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong X");
            }
        }
        while (true) {
            try {
                System.out.println("Y: ");
                tmp = reader.readLine();
                Y = Float.parseFloat(tmp);
                cor.setY(Y);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong Y");
            }
        }
        setCoordinates(cor);

        System.out.println("From: ");
        Location from = new Location(0L,0, "asd");
        String FName;
        Long FX;
        int FY;
        while (true) {
            try {
                System.out.println("X: ");
                tmp = reader.readLine();
                FX = Long.parseLong(tmp);
                from.setX(FX);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong X");
            }
        }
        while (true) {
            try {
                System.out.println("Y: ");
                tmp = reader.readLine();
                FY = Integer.parseInt(tmp);
                from.setY(FY);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong Y");
            }
        }
        while (true) {
            try {
                System.out.println("name: ");
                FName = reader.readLine();
                from.setName(FName);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong name");
            }
        }
        setFrom(from);

        System.out.println("To: ");
        Location to = new Location(0L,0, "asd");
        String TName;
        Long TX;
        int TY;
        while (true) {
            try {
                System.out.println("X: ");
                tmp = reader.readLine();
                TX = Long.parseLong(tmp);
                to.setX(TX);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong X");
            }
        }
        while (true) {
            try {
                System.out.println("Y: ");
                tmp = reader.readLine();
                TY = Integer.parseInt(tmp);
                to.setY(TY);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong Y");
            }
        }
        while (true) {
            try {
                System.out.println("name: ");
                TName = reader.readLine();
                to.setName(TName);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong name");
            }
        }
        setTo(to);
        Integer distance = 0;
        while (true) {
            try {
                System.out.println("distance: ");
                tmp = reader.readLine();
                distance = Integer.parseInt(tmp);
                setDistance(distance);
                break;
            } catch (RuntimeException | java.io.IOException ex) {
                System.out.println("wrong distance");
            }
        }
    }


    @Override
    public String convertToCsv() {
        return null;
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
        return 0;
    }

}

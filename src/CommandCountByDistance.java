import java.util.Arrays;
import java.util.Objects;

/**
 * The type Command count by distance.
 */
public class CommandCountByDistance extends Command{
    private final Integer distance;

    /**
     * Instantiates a new Command count by distance.
     *
     * @param data     the data
     * @param distance the distance
     */
    CommandCountByDistance(DeqCollection<?> data, Integer distance) {
        super(data);
        this.distance = distance;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        int count = 0;
        for(T i : data.getStorage().toArray(data.createContentsArray(data.getStorage().size()))){
            if(Objects.equals(i.getDistance(), this.distance)) count++;
        }
        System.out.println(count);
    }

    @Override
    public void execute() {
        fooHelper(data);
    }
}

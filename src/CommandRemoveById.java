import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Command remove by id.
 */
public class CommandRemoveById extends Command{
    private long id;

    /**
     * Instantiates a new Command remove by id.
     *
     * @param data the data
     * @param id   the id
     */
    CommandRemoveById(DeqCollection<?> data, Long id) {
        super(data);
        this.id = id;
    }
    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        for (T i : arr) {
            if(i.getId() == id){
                data.getStorage().remove(i);
                return;
            }
        }
        System.out.println("Такого id нет");
    }
    @Override
    public void execute() {
        fooHelper(data);
    }
}

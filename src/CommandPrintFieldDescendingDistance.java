import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * The type Command print field descending distance.
 */
public class CommandPrintFieldDescendingDistance extends Command{
    /**
     * Instantiates a new Command print field descending distance.
     *
     * @param data the data
     */
    CommandPrintFieldDescendingDistance(DeqCollection<?> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        java.util.Arrays.sort(arr, T::compareTo);
        List<T> list = Arrays.asList(arr);
        Collections.reverse(list);
        for (T i: list) {
            System.out.println(i.getDistance());
        }
    }

    @Override
    public void execute() {
        fooHelper(data);
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * The type Command remove lower.
 */
public class CommandRemoveLower extends Command{
    /**
     * The Reader.
     */
    BufferedReader reader;
    /**
     * The Is standard input.
     */
    boolean isStandardInput;

    /**
     * Instantiates a new Command remove lower.
     *
     * @param data            the data
     * @param reader          the reader
     * @param isStandardInput the is standard input
     */
    CommandRemoveLower(DeqCollection<?> data, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }
    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));

        T tmp = (T)data.createContents();
        tmp.loadFromStandardInput(reader, isStandardInput);

        for (T i : arr) {
            if(tmp.compareTo(i) > 0){
                data.getStorage().remove(i);
            }
        }
    }
    @Override
    public void execute() {
        fooHelper(data);
    }
}

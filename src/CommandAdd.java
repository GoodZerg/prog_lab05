import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The type Command add.
 */
public class CommandAdd extends Command{
    /**
     * The Reader.
     */
    BufferedReader reader;
    /**
     * The Is standard input.
     */
    boolean isStandardInput;

    /**
     * Instantiates a new Command add.
     *
     * @param data            the data
     * @param reader          the reader
     * @param isStandardInput the is standard input
     */
    CommandAdd(DeqCollection<?> data, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T tmp = (T)data.createContents();
        tmp.loadFromStandardInput(reader, isStandardInput);
        data.getStorage().add(tmp);
    }


    @Override
    public void execute() {
        fooHelper(data);
    }
}

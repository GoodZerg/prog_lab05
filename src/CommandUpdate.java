import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The type Command update.
 */
public class CommandUpdate extends Command{
    private Long id;
    /**
     * The Reader.
     */
    BufferedReader reader;
    /**
     * The Is standard input.
     */
    boolean isStandardInput;

    /**
     * Instantiates a new Command update.
     *
     * @param data            the data
     * @param id              the id
     * @param reader          the reader
     * @param isStandardInput the is standard input
     */
    CommandUpdate(DeqCollection<?> data, Long id, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.id = id;
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        for (T i : arr) {
            if(i.getId() == id){
                i.loadFromStandardInput(reader, isStandardInput);
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

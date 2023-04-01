import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

public class CommandRemoveLower extends Command{
    BufferedReader reader;
    boolean isStandardInput;
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

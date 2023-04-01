import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandAdd extends Command{
    BufferedReader reader;
    boolean isStandardInput;
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

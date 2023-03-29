import java.util.Optional;

public class CommandRemoveLower extends Command{
    CommandRemoveLower(DeqCollection<?> data) {
        super(data);
    }
    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));

        T tmp = (T)data.createContents();
        tmp.loadFromStandardInput();

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

import java.util.Optional;

public class CommandAddIfMax extends Command{
    CommandAddIfMax(DeqCollection<?> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T tmp = (T)data.createContents();
        tmp.loadFromStandardInput();
        Optional<T> max = data.findMax();
        T _max;
        if(max.isPresent()){
            _max = max.get();
        } else {
            data.getStorage().add(tmp);
            return;
        }
        if(tmp.compareTo(_max) > 0 ) data.getStorage().add(tmp);
    }

    @Override
    public void execute() {
        fooHelper(data);
    }
}

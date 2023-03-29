import java.util.Optional;

public class CommandMaxByCoordinates extends Command{
    CommandMaxByCoordinates(DeqCollection<?> data) {
        super(data);
    }
    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        Optional<T> max = data.findMaxByCord();
        max.ifPresent(t -> System.out.println(t.toString()));
    }
    @Override
    public void execute() {
        fooHelper(data);
    }
}

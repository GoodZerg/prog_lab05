import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Optional;


public class DeqCollection<T extends Collectible & Comparable<T>> {

    private ArrayDeque<T> storage = new ArrayDeque<T>(0);
    private java.time.LocalDate creationDate;
    private final Factory<T> factory;
    private final ArrayFactory<T> arrayFactory;

    DeqCollection(Factory<T> factory, ArrayFactory<T> arrayFactory){
        this.factory = factory;
        this.arrayFactory = arrayFactory;
    }
    public void load(FileReader fileReader){
        ///TODO
        creationDate = java.time.LocalDate.now();
    }
    public T createContents() {
        return factory.create();
    }
    public T[] createContentsArray(int n) {
        return arrayFactory.create(n);
    }
    public ArrayDeque<T> getStorage() {
        return storage;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Optional<T> findMax(){return storage.stream().max(T::compareTo);}
    public Optional<T> findMaxByCord(){return storage.stream().max(T::compareByCord);}
}

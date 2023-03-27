import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Vector;


public class DeqCollection<T extends Collectible & Comparable<T>> {

    private ArrayDeque<T> storage = new ArrayDeque<T>(0);
    private java.time.LocalDate creationDate;
    private final Factory<T> factory;

    DeqCollection(Factory<T> factory){
        this.factory = factory;
    }
    public void load(FileReader fileReader){
        ///TODO
        creationDate = java.time.LocalDate.now();
    }

    public T createContents() {
        return factory.create();
    }
    public ArrayDeque<T> getStorage() {
        return storage;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}

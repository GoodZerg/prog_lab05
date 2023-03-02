import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.function.Supplier;

public class DeqCollection<T extends Collectible & Comparable<T>> {

    private ArrayDeque<T> storage = new ArrayDeque<T>();
    private java.time.LocalDate creationDate;

    public void load(FileReader fileReader){
        ///TODO

        creationDate = java.time.LocalDate.now();
    }
    private final Supplier<? extends T> ctor;

    DeqCollection(Supplier<? extends T> ctor) {
        this.ctor = Objects.requireNonNull(ctor);
    }

    public T myMethod() {
        return ctor.get();
    }
    public ArrayDeque<T> getStorage() {
        return storage;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}

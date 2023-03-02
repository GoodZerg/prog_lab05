import java.time.LocalDate;
import java.util.ArrayDeque;
public class DeqCollection<T extends Collectible & Comparable<T>> {
    private ArrayDeque<T> storage;
    private java.time.LocalDate creationDate;
    public void load(FileReader fileReader){
        ///TODO
        creationDate = java.time.LocalDate.now();
    }

    public ArrayDeque<T> getStorage() {
        return storage;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}

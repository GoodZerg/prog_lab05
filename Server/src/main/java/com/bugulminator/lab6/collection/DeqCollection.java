package com.bugulminator.lab6.collection;

import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.io.FileReader;
import com.bugulminator.lab6.io.OutputHandler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Optional;


/**
 * The type Deq collection.
 *
 * @param <T> the type parameter
 */
public class DeqCollection<T extends Collectible & Comparable<T>> {

    private ArrayDeque<T> storage = new ArrayDeque<>(0);
    private LocalDate creationDate;
    private final Factory<T> factory;
    private final ArrayFactory<T> arrayFactory;

    /**
     * Instantiates a new Deq collection.
     *
     * @param factory      the factory
     * @param arrayFactory the array factory
     */
    public DeqCollection(Factory<T> factory, ArrayFactory<T> arrayFactory) {
        this.factory = factory;
        this.arrayFactory = arrayFactory;
    }

    /**
     * Load.
     *
     */
    public void load() {
        creationDate = LocalDate.now();
    }

    /**
     * Create contents t.
     *
     * @return the t
     */
    public T createContents() {
        return factory.create();
    }

    /**
     * Create contents array t [ ].
     *
     * @param n the n
     * @return the t [ ]
     */
    public T[] createContentsArray(int n) {
        return arrayFactory.create(n);
    }

    /**
     * Gets storage.
     *
     * @return the storage
     */
    public ArrayDeque<T> getStorage() {
        return storage;
    }

    /**
     * Gets storage.
     *
     * @return the storage
     */
    public void setStorage(ArrayDeque<T> storage) {
        this.storage = storage;
    }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Find max optional.
     *
     * @return the optional
     */
    public Optional<T> findMax(){return storage.stream().max(T::compareTo);}

    /**
     * Find max by cord optional.
     *
     * @return the optional
     */
    public Optional<T> findMaxByCord(){return storage.stream().max(T::compareByCord);}
}

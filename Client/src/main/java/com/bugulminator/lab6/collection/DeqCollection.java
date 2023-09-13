package com.bugulminator.lab6.collection;

import com.bugulminator.lab6.io.FileReader;
import com.bugulminator.lab6.io.OutputHandler;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Optional;


/**
 * The type Deq collection.
 *
 * @param <T> the type parameter
 */
public class DeqCollection<T extends Collectible & Comparable<T>> {

    private final ArrayDeque<T> storage = new ArrayDeque<>(0);
    private LocalDate creationDate;
    private final Factory<T> factory;
    private final ArrayFactory<T> arrayFactory;
    private final OutputHandler output;

    /**
     * Instantiates a new Deq collection.
     *
     * @param factory      the factory
     * @param arrayFactory the array factory
     * @param output       the output
     */
    public DeqCollection(Factory<T> factory, ArrayFactory<T> arrayFactory, OutputHandler output) {
        this.factory = factory;
        this.arrayFactory = arrayFactory;
        this.output = output;
    }

    /**
     * Load.
     *
     * @param fileReader the file reader
     */
    public void load(FileReader fileReader){
        creationDate = LocalDate.now();
        while(fileReader.ready()){
            T t = factory.create();
            t.loadFromCsv(fileReader.get());
            storage.add(t);
        }
        long max_id = 0;
        for (T i : storage) {
            if(i.getId() > max_id) max_id = i.getId();
        }
        if(!storage.isEmpty()) storage.getFirst().setStartID(max_id);
    }

    /**
     * Save.
     */
    public void save(){
        output.start();
        for (T i : storage) {
            output.writeLine(i.convertToCsv()+"\n");
        }
        output.close();
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

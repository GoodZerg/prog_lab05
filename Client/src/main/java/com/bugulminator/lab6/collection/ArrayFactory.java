package com.bugulminator.lab6.collection;

/**
 * The interface Array factory.
 *
 * @param <T> the type parameter
 */
public interface ArrayFactory<T> {
    /**
     * Create t [ ].
     *
     * @param n the count
     * @return the t [ ]
     */
    T[] create(int n);
}

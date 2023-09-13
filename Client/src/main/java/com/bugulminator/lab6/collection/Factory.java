package com.bugulminator.lab6.collection;

/**
 * The interface main.lab6.Factory.
 *
 * @param <T> the type parameter
 */
public interface Factory<T> {
    /**
     * Create t.
     *
     * @return the t
     */
    T create();
}

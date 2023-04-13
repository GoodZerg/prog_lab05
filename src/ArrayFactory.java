/**
 * The interface Array factory.
 *
 * @param <T> the type parameter
 */
interface ArrayFactory<T> {
    /**
     * Create t [ ].
     *
     * @param n the count
     * @return the t [ ]
     */
    T[] create(int n);
}

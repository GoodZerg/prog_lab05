/**
 * The interface Factory.
 *
 * @param <T> the type parameter
 */
interface Factory<T> {
    /**
     * Create t.
     *
     * @return the t
     */
    T create();
}

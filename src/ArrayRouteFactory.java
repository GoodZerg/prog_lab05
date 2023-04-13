/**
 * The type Array route factory.
 */
public class ArrayRouteFactory implements ArrayFactory{
    @Override
    public Route[] create(int n) {
        return new Route[n];
    }
}

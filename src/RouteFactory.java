/**
 * The type Route factory.
 */
public class RouteFactory implements Factory{
    @Override
    public Route create() {
        return new Route();
    }
}

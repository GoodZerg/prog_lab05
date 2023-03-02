public class CommandCountByDistance extends Command{
    private Integer distance;
    CommandCountByDistance(DeqCollection<?> data, Integer distance) {
        super(data);
        this.distance = distance;
    }

    @Override
    public void execute() {

    }
}

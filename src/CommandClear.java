/**
 * The type Command clear.
 */
public class CommandClear extends Command{
    /**
     * Instantiates a new Command clear.
     *
     * @param data the data
     */
    CommandClear(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        data.getStorage().clear();
    }
}

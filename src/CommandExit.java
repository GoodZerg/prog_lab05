/**
 * The type Command exit.
 */
public class CommandExit extends Command{
    /**
     * Instantiates a new Command exit.
     *
     * @param data the data
     */
    CommandExit(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}

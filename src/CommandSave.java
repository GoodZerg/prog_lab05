/**
 * The type Command save.
 */
public class CommandSave extends Command{
    /**
     * Instantiates a new Command save.
     *
     * @param data the data
     */
    CommandSave(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        data.save();
    }
}

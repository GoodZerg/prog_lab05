/**
 * The type Command.
 */
public abstract class Command {
    /**
     * The Data.
     */
    protected DeqCollection<?> data;

    /**
     * Instantiates a new Command.
     *
     * @param data the data
     */
    Command(DeqCollection<?> data){
        this.data = data;
    }

    /**
     * Execute.
     */
    public abstract void execute();
}

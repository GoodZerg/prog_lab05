/**
 * The type Command info.
 */
public class CommandInfo extends Command{
    /**
     * Instantiates a new Command info.
     *
     * @param data the data
     */
    CommandInfo(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        System.out.println("type : " + data.getStorage().getClass());
        System.out.println("creationDate : " + data.getCreationDate());
        System.out.println("number of elements : " + data.getStorage().size());
    }
}

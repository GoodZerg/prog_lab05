public class CommandClear extends Command{
    CommandClear(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        data.getStorage().clear();
    }
}

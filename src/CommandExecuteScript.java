public class CommandExecuteScript extends Command{
    private String fileName;
    CommandExecuteScript(DeqCollection<?> data, String fileName) {
        super(data);
        this.fileName = fileName;
    }

    @Override
    public void execute() {

    }
}

public class CommandUpdate extends Command{
    private Long id;
    CommandUpdate(DeqCollection<?> data, Long id) {
        super(data);
        this.id = id;
    }

    @Override
    public void execute() {

    }
}

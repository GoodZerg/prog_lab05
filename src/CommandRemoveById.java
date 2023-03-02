public class CommandRemoveById extends Command{
    private Long id;
    CommandRemoveById(DeqCollection<?> data, Long id) {
        super(data);
        this.id = id;
    }

    @Override
    public void execute() {

    }
}

public class CommandInfo extends Command{
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

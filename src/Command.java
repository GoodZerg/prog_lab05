public abstract class Command {
    protected DeqCollection<?> data;
    Command(DeqCollection<?> data){
        this.data = data;
    }
    public abstract void execute();
}

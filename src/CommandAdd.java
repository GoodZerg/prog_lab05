public class CommandAdd extends Command{
    CommandAdd(DeqCollection<?> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T tmp = (T)data.createContents();
        tmp.loadFromStandardInput();
        data.getStorage().add(tmp);
    }


    @Override
    public void execute() {
        System.out.println("add excecute");
        fooHelper(data);
    }
}

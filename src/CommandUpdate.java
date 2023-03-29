public class CommandUpdate extends Command{
    private Long id;
    CommandUpdate(DeqCollection<?> data, Long id) {
        super(data);
        this.id = id;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        for (T i : arr) {
            if(i.getId() == id){
                i.loadFromStandardInput();
                return;
            }
        }
        System.out.println("Такого id нет");
    }

    @Override
    public void execute() {
        fooHelper(data);
    }
}

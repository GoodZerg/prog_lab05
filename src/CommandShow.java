public class CommandShow extends Command{
    CommandShow(DeqCollection<?> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data){
        for (Collectible i : data.getStorage()){
            System.out.println(i.toString());
        }
    }

    @Override
    public void execute() {
        fooHelper(data);
    }
}

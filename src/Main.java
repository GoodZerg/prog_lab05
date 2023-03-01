public class Main {
    public static void main(String[] args) {
        try {
            InputHandler inputHandler = new InputHandler();
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("Hello world!");
    }
}
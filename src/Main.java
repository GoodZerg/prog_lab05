public class Main {
    public static void main(String[] args) {
        try {
            InputHandler inputHandler = new InputHandler(new DeqCollection<Route>(Route::new, Route[]::new));
            inputHandler.start();
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("PASS");
    }
}
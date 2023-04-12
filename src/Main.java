public class Main {
    public static void main(String[] args) {
        String file_name //= args[0];
            = "start.txt"; //TODO REMOVE DEBUG
        try {
            InputHandler inputHandler = new InputHandler(new DeqCollection<Route>(Route::new, Route[]::new));
            inputHandler.start(file_name);
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("PASS");
    }
}
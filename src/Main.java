public class Main {
    public static void main(String[] args) {
        String file_name //= args[0];
            = "src\\start.txt"; //TODO REMOVE DEBUG
        try {
            InputHandler inputHandler = new InputHandler(new DeqCollection<Route>(Route::new, Route[]::new, new OutputHandler(file_name)));
            inputHandler.start(file_name);
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("PASS");
    }
}
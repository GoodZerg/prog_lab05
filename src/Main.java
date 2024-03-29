/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Need param");
            return;
        }
        String file_name = args[0];
//            = "src\\start.txt"; //TODO REMOVE DEBUG
        try {
            InputHandler inputHandler = new InputHandler(new DeqCollection<Route>(Route::new, Route[]::new, new OutputHandler(file_name)));
            inputHandler.start(file_name);
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }
}
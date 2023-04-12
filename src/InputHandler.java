import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public class InputHandler {
    private final DeqCollection<?> data;
    private final Invoker parser;
    InputHandler(DeqCollection<?> data){
        this.data = data;
        this.parser = new Invoker(data);
    }

    public void start(String file_name) {
        data.load(new FileReader(file_name));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                try {
                    parser.parseCommand(reader.readLine(), reader, true);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Error command");
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (java.io.IOException ex){
            System.out.println("Error in reading command");
        }
    }

}

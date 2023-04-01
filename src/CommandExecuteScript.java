import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Vector;

public class CommandExecuteScript extends Command{
    private String fileName;
    CommandExecuteScript(DeqCollection<?> data, String fileName) {
        super(data);
        this.fileName = fileName;
    }
    private boolean checkRecursion(BufferedReader reader, Vector<String> callStack) {
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                String[] words = line.split(" ");
                if(Objects.equals(words[0], "execute_script")){
                    for (String i : callStack) {
                        if(Objects.equals(i, words[1])){
                            return false;
                        }
                    }
                    callStack.add(words[1]);
                    if(checkRecursion(readFile(words[1]), callStack)){
                        callStack.remove(words[1]);
                    }else{
                        return false;
                    }
                }
            }
        }catch (IOException e){
            System.out.println("Cannot read the file");
        }
        return true;
    }

    public BufferedReader readFile(String name) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(name)).getFile());
        InputStream inputStream = new FileInputStream(file);
        return new BufferedReader(new InputStreamReader(inputStream));
    }


    @Override
    public void execute() {
        BufferedReader reader;
            try {
                reader = readFile(fileName);
            } catch(FileNotFoundException | NullPointerException e){
                System.out.println("Cannot open file");
                return;
            }
        Vector<String> callStack = new Vector<>(1);
        callStack.add(fileName);
        if(checkRecursion(reader, callStack)){
            try {
                reader = readFile(fileName);
            } catch(FileNotFoundException | NullPointerException e){
                System.out.println("Cannot open file");
                return;
            }
            Invoker localScriptInvoker = new Invoker(data);
            try{
                while(reader.ready()){
                    try {
                        localScriptInvoker.parseCommand(reader.readLine(), reader, false);
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                             IllegalAccessException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }catch (IOException e){
                System.out.println("Cannot read the file");
            }
        } else {
            System.out.println("recursion");
        }

    }
}

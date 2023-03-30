import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Vector;

public class Invoker {
    private final DeqCollection<?> data;
    private static final Vector<Command> doneCommands = new Vector<Command>();

    public static final int command_num = 16;
    public static Vector<Command> getDoneCommands(){
        return doneCommands;
    }

    public static class _CommandInformation{
        public String name;
        public Class<?> command_class;
        public int argument_count;
        _CommandInformation(String name, Class<?> command_class, int argument_count){
            this.name = name;
            this.command_class = command_class;
            this.argument_count = argument_count;
        }

    }

    public static Vector<_CommandInformation> commandsInfo;

    static{
        commandsInfo = new Vector<_CommandInformation>(command_num);
        commandsInfo.add(new _CommandInformation(
                "help",
                CommandHelp.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "info",
                CommandInfo.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "show",
                CommandShow.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "add",
                CommandAdd.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "update",
                CommandUpdate.class,
                1));
        commandsInfo.add(new _CommandInformation(
                "remove_by_id",
                CommandRemoveById.class,
                1));
        commandsInfo.add(new _CommandInformation(
                "clear",
                CommandClear.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "execute_script",
                CommandExecuteScript.class,
                1));
        commandsInfo.add(new _CommandInformation(
                "exit",
                CommandExit.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "add_if_max",
                CommandAddIfMax.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "remove_lower",
                CommandRemoveLower.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "history",
                CommandHistory.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "max_by_coordinates",
                CommandMaxByCoordinates.class,
                0));
        commandsInfo.add(new _CommandInformation(
                "count_by_distance",
                CommandCountByDistance.class,
                1));
        commandsInfo.add(new _CommandInformation(
                "print_field_descending_distance",
                CommandPrintFieldDescendingDistance.class,
                0));
    }

    public  static _CommandInformation findCommandByClass(Class<?> command_class){
        for (_CommandInformation i :
                commandsInfo){
            if(command_class == i.command_class) return i;
        }
        return new _CommandInformation("Error", Command.class, 0);
    }


    private void findCommandByName(String[] words)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String first_word = words[0];
        for (_CommandInformation i :
                commandsInfo) {
            if(Objects.equals(first_word, i.name)){
                if(words.length == i.argument_count + 1){
                    if(words.length == 1){
                        execute((Command) i.command_class.getDeclaredConstructor(DeqCollection.class)
                                .newInstance(data));
                        return;
                    } else if (Objects.equals("update", i.name) || Objects.equals("remove_by_id", i.name)){
                        execute((Command) i.command_class.getDeclaredConstructor(DeqCollection.class, Long.class)
                                .newInstance(data,Long.parseLong(words[1])));
                        return;
                    } else if (Objects.equals("count_by_distance", i.name)) {
                        execute((Command) i.command_class.getDeclaredConstructor(DeqCollection.class, Integer.class)
                                .newInstance(data, Integer.parseInt(words[1])));
                        return;
                    } else if(Objects.equals("execute_script", i.name)){
                        execute((Command) i.command_class.getDeclaredConstructor(DeqCollection.class, String.class)
                                .newInstance(data, words[1]));
                        return;
                    }
                } else {
                    throw new IllegalArgumentException("");
                }
            }
        }
        throw new IllegalArgumentException("wrong command");
    }

    public void execute(Command command){
        command.execute();
        doneCommands.add(command);
    }
    public Invoker(DeqCollection<?> data) {
        this.data = data;
    }

    public void parseCommand(String str)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(str == null)
            throw new RuntimeException("null command");
        String[] words = str.split(" ");
        findCommandByName(words);
    }
}


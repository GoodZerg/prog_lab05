import jdk.jfr.Unsigned;

import java.util.Vector;

import static java.lang.Math.max;

/**
 * The type Command history.
 */
public class CommandHistory extends Command{
    private static final int max_history = 6;

    /**
     * Instantiates a new Command history.
     *
     * @param data the data
     */
    CommandHistory(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        Vector<Command> history = Invoker.getDoneCommands();
        if(history.isEmpty()){
            System.out.println("History is empty");
        }
        int sizeHistory = history.size();
        for(int i = sizeHistory - 1; i >= max(sizeHistory - max_history, 0); i--){
            Invoker._CommandInformation command = Invoker.findCommandByClass(history.get(i).getClass());
            System.out.println(i + 1 + ": " + command.name);
        }
        System.out.println("...");
    }
}

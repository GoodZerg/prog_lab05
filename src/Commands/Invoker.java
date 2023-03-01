package Commands;

import Commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;
/**
 * <b>Invoker</b> calls a specific command
 * knowing only its interface
 */
public class Invoker {
    private final Vector<Command> doneCommands = new Vector<Command>();
    /**
     * <b>execute</b> call a command
     */
    public void execute(@NotNull Command command){
        command.execute();
        doneCommands.add(command);
    }
}

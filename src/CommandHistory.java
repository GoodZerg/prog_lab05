import jdk.jfr.Unsigned;

import java.util.Vector;

import static java.lang.Math.max;

public class CommandHistory extends Command{
    CommandHistory(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        Vector<Command> history = Invoker.getDoneCommands();
        int sizeHistory = history.size();
        for(int i = sizeHistory; i < max(sizeHistory - 6, 0); i++){
            //System.out.println(
        }
    }
}

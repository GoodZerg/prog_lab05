public class CommandParser {
    private final DeqCollection<?> data;
    private final Invoker invoker = new Invoker();

    public CommandParser(DeqCollection<?> data) {
        this.data = data;
    }

    public void parseCommand(String str){
        if(str == null)
            throw new RuntimeException("null command");
        String[] words = str.split(" ");
        switch (words[0]) {
            case "help":
                invoker.execute(new CommandHelp(data));
                break;
            case "info":
                invoker.execute(new CommandInfo(data));
                break;
            default:
                throw new IllegalArgumentException("wrong command");
        }
    }
}


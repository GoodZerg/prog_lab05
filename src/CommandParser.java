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
            case "help"                            -> { if (words.length == 1) invoker.execute(new CommandHelp(data));
                                                            else throw new IllegalArgumentException(""); }
            case "info"                            -> { if (words.length == 1) invoker.execute(new CommandInfo(data));
                                                        else throw new IllegalArgumentException(""); }
            case "show"                            -> { if (words.length == 1) invoker.execute(new CommandShow(data));
                                                        else throw new IllegalArgumentException(""); }
            case "add"                            -> { if (words.length == 1) invoker.execute(new CommandAdd(data));
                                                        else throw new IllegalArgumentException(""); }
            case "update"                          -> { if (words.length == 2) invoker.execute(new CommandUpdate(data,
                                                            Long.parseLong(words[1])));
                                                        else throw new IllegalArgumentException(""); }
            case "remove_by_id"                    -> { if (words.length == 2) invoker.execute(new CommandRemoveById(data,
                                                            Long.parseLong(words[1])));
                                                        else throw new IllegalArgumentException(""); }
            case "clear"                           -> { if (words.length == 1) invoker.execute(new CommandClear(data));
                                                        else throw new IllegalArgumentException(""); }
            case "save"                           -> { if (words.length == 1) invoker.execute(new CommandSave(data));
                                                        else throw new IllegalArgumentException(""); }
            case "execute_script"                  -> { if (words.length == 2) invoker.execute(new CommandExecuteScript(data,
                                                            words[1]));
                                                        else throw new IllegalArgumentException(""); }
            case "exit"                            -> { if (words.length == 1) invoker.execute(new CommandExit(data));
                                                        else throw new IllegalArgumentException(""); }
            case "add_if_max"                      -> { if (words.length == 1) invoker.execute(new CommandAddIfMax(data));
                                                        else throw new IllegalArgumentException(""); }
            case "remove_lower"                    -> { if (words.length == 1) invoker.execute(new CommandRemoveLower(data));
                                                        else throw new IllegalArgumentException(""); }
            case "history"                         -> { if (words.length == 1) invoker.execute(new CommandHistory(data));
                                                        else throw new IllegalArgumentException(""); }
            case "max_by_coordinates"              -> { if (words.length == 1) invoker.execute(new CommandMaxByCoordinates(data));
                                                        else throw new IllegalArgumentException(""); }
            case "count_by_distance"               -> { if (words.length == 2) invoker.execute(new CommandCountByDistance(data,
                                                            Integer.parseInt(words[1])));
                                                        else throw new IllegalArgumentException(""); }
            case "print_field_descending_distance" -> { if (words.length == 1) invoker.execute(new CommandPrintFieldDescendingDistance(data));
                                                        else throw new IllegalArgumentException(""); }
            default                                -> throw new IllegalArgumentException("wrong command");
        }
    }
}


package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;

import java.util.Map;

public class CommandCheckId extends Command implements RemoteCommand {

    public CommandCheckId(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        System.err.println("This command is not executable on server");
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        if (data == null) {
            return false;
        }
        if (!data.containsKey("id")) {
            return false;
        }
        return data.get("id") instanceof Long;
    }

    @Override
    public String process(Map<String, Object> context) {
        Long id = (Long) context.get("id");
        for (var it : data.getStorage()) {
            if (it.getId() == id) {
                return "true";
            }
        }
        return "false";
    }
}

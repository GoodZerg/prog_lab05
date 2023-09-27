package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;

import java.util.Map;

/**
 * The type main.lab6.Command clear.
 */
public class CommandClear extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command clear.
     *
     * @param data the data
     */
    public CommandClear(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        data.getStorage().clear();
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        return data == null;
    }

    @Override
    public ResponseEntity process(Map<String, Object> context) {
        data.getStorage().clear();
        return new ResponseEntity("Storage cleared");
    }
}

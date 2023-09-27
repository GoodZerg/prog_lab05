package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DatabaseManager;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;

/**
 * The type main.lab6.Command save.
 */
public class CommandSave extends Command {
    /**
     * Instantiates a new main.lab6.Command save.
     *
     * @param data the data
     */
    public CommandSave(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        DatabaseManager.save(data);
    }
}

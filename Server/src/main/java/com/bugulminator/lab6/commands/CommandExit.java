package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;

/**
 * The type main.lab6.Command exit.
 */
public class CommandExit extends Command {
    /**
     * Instantiates a new main.lab6.Command exit.
     *
     * @param data the data
     */
    public CommandExit(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}

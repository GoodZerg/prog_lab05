package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;

public abstract class CommandCheckId extends Command {
    /**
     * Instantiates a new main.lab6.Command.
     *
     * @param data the data
     */
    public CommandCheckId(DeqCollection<?> data) {
        super(data);
    }
}

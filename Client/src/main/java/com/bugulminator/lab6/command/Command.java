package com.bugulminator.lab6.command;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.exceptions.NotAuthorizedException;

/**
 * The type main.lab6.Command.
 */
public abstract class Command {
    /**
     * The Data.
     */
    protected DeqCollection<?> data;

    /**
     * Instantiates a new main.lab6.Command.
     *
     * @param data the data
     */
    public Command(DeqCollection<?> data){
        this.data = data;
    }

    /**
     * Execute.
     */
    public abstract void execute() throws NotAuthorizedException;
}

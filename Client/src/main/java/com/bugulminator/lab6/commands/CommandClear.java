package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.network.C2SPackage;

/**
 * The type main.lab6.Command clear.
 */
public class CommandClear extends Command {
    /**
     * Instantiates a new main.lab6.Command clear.
     *
     * @param data the data
     */
    public CommandClear(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() {
        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        this.getClass(),
                        null
                )
        );
    }
}

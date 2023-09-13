package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.network.C2SPackage;

/**
 * The type main.lab6.Command info.
 */
public class CommandInfo extends Command {
    /**
     * Instantiates a new main.lab6.Command info.
     *
     * @param data the data
     */
    public CommandInfo(DeqCollection<?> data) {
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
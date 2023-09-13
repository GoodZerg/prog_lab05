package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.network.C2SPackage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type main.lab6.Command print field descending distance.
 */
public class CommandPrintFieldDescendingDistance extends Command {
    /**
     * Instantiates a new main.lab6.Command print field descending distance.
     *
     * @param data the data
     */
    public CommandPrintFieldDescendingDistance(DeqCollection<?> data) {
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

package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.AuthHandler;
import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.Invoker;
import com.bugulminator.lab6.exceptions.NotAuthorizedException;
import com.bugulminator.lab6.network.C2SPackage;

import java.util.Vector;

import static java.lang.Math.max;

/**
 * The type main.lab6.Command history.
 */
public class CommandHistory extends Command {
    private static final int max_history = 6;

    /**
     * Instantiates a new main.lab6.Command history.
     *
     * @param data the data
     */
    public CommandHistory(DeqCollection<?> data) {
        super(data);
    }

    @Override
    public void execute() throws NotAuthorizedException {
        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        this.getClass(),
                        null,
                        AuthHandler.getInstance().getCredentials()
                )
        );
    }
}

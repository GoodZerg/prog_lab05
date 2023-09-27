package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.AuthHandler;
import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.exceptions.NotAuthorizedException;
import com.bugulminator.lab6.network.C2SPackage;

/**
 * The type main.lab6.Command show.
 */
public class CommandShow extends Command {
    /**
     * Instantiates a new main.lab6.Command show.
     *
     * @param data the data
     */
    public CommandShow(DeqCollection<?> data) {
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

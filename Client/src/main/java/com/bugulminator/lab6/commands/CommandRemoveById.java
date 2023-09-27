package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.AuthHandler;
import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.exceptions.NotAuthorizedException;
import com.bugulminator.lab6.network.C2SPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type main.lab6.Command remove by id.
 */
public class CommandRemoveById extends Command {
    private final long id;

    /**
     * Instantiates a new main.lab6.Command remove by id.
     *
     * @param data the data
     * @param id   the id
     */
    public CommandRemoveById(DeqCollection<?> data, Long id) {
        super(data);
        this.id = id;
    }

    @Override
    public void execute() throws NotAuthorizedException {
        Map<String, Object> context = new HashMap<>();
        context.put("id", (int) id);
        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        this.getClass(),
                        context,
                        AuthHandler.getInstance().getCredentials()
                )
        );
    }
}

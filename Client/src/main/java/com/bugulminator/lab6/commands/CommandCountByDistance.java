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
 * The type main.lab6.Command count by distance.
 */
public class CommandCountByDistance extends Command {
    private final Integer distance;

    /**
     * Instantiates a new main.lab6.Command count by distance.
     *
     * @param data     the data
     * @param distance the distance
     */
    public CommandCountByDistance(DeqCollection<?> data, Integer distance) {
        super(data);
        this.distance = distance;
    }

    @Override
    public void execute() throws NotAuthorizedException {
        Map<String, Object> context = new HashMap<>();
        context.put("distance", distance);

        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        this.getClass(),
                        context,
                        AuthHandler.getInstance().getCredentials()
                )
        );
    }
}

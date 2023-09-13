package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.network.C2SPackage;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The type main.lab6.Command remove lower.
 */
public class CommandRemoveLower extends Command {
    /**
     * The Reader.
     */
    BufferedReader reader;
    /**
     * The Is standard input.
     */
    boolean isStandardInput;

    /**
     * Instantiates a new main.lab6.Command remove lower.
     *
     * @param data            the data
     * @param reader          the reader
     * @param isStandardInput the is standard input
     */
    public CommandRemoveLower(DeqCollection<?> data, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }

    @Override
    public void execute() {
        Route tmp = (Route) data.createContents();
        tmp.loadFromStandardInput(reader, isStandardInput);

        Map<String, Object> context = new HashMap<>();
        context.put("name", tmp.getName());
        context.put("coord_x", tmp.getCoordinates().getX());
        context.put("coord_y", tmp.getCoordinates().getY());
        context.put("creation_date", tmp.getCreationDate());
        context.put("from_x", tmp.getFrom().getX());
        context.put("from_y", tmp.getFrom().getY());
        context.put("from_name", tmp.getFrom().getName());
        context.put("to_x", tmp.getTo().getX());
        context.put("to_y", tmp.getTo().getY());
        context.put("to_name", tmp.getTo().getName());
        context.put("distance", tmp.getDistance());

        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        this.getClass(),
                        context
                )
        );
    }
}

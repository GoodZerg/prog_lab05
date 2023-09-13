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
import java.util.Objects;

/**
 * The type main.lab6.Command update.
 */
public class CommandUpdate extends Command {
    private final Long id;
    /**
     * The Reader.
     */
    BufferedReader reader;
    /**
     * The Is standard input.
     */
    boolean isStandardInput;

    /**
     * Instantiates a new main.lab6.Command update.
     *
     * @param data            the data
     * @param id              the id
     * @param reader          the reader
     * @param isStandardInput the is standard input
     */
    public CommandUpdate(DeqCollection<?> data, Long id, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.id = id;
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        for (T i : arr) {
            if (i.getId() == id) {
                i.loadFromStandardInput(reader, isStandardInput);
                return;
            }
        }
        System.out.println("Такого id нет");
    }

    @Override
    public void execute() {
        Map<String, Object> context = new HashMap<>();
        context.put("id", id);

        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        CommandCheckId.class,
                        context
                )
        );

        while (NetworkHandler.getInstance().getLastResponse() == null) {
            Thread.onSpinWait();
        }
        if (Objects.equals(NetworkHandler.getInstance().getLastResponse(), NetworkHandler.TRUE_RESPONSE)) {
            Route tmp = (Route) data.createContents();
            tmp.loadFromStandardInput(reader, isStandardInput);

            context = new HashMap<>();
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
            context.put("id", id);

            NetworkHandler.getInstance().sendPackage(
                    new C2SPackage(
                            this.getClass(),
                            context
                    )
            );
        } else {
            System.out.println("No element with such id");
        }
    }
}

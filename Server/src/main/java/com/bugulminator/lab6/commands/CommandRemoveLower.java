package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Coordinates;
import com.bugulminator.lab6.collection.data.Location;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;

import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.Map;

/**
 * The type main.lab6.Command remove lower.
 */
public class CommandRemoveLower extends Command implements RemoteCommand {
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
    public CommandRemoveLower(DeqCollection<Route> data, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));

        T tmp = data.createContents();
        tmp.loadFromStandardInput(reader, isStandardInput);

        for (T i : arr) {
            if (tmp.compareTo(i) > 0) {
                data.getStorage().remove(i);
            }
        }
    }

    @Override
    public void execute() {
        fooHelper(data);
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        if (data == null) {
            return false;
        }
        if (!data.containsKey("name")
                || !data.containsKey("coord_x")
                || !data.containsKey("coord_y")
                || !data.containsKey("creation_date")
                || !data.containsKey("from_x")
                || !data.containsKey("from_y")
                || !data.containsKey("from_name")
                || !data.containsKey("to_x")
                || !data.containsKey("to_y")
                || !data.containsKey("to_name")
                || !data.containsKey("distance")) {
            return false;
        }

        if (!(data.get("name") instanceof String)
                || !(data.get("coord_x") instanceof Long)
                || !(data.get("coord_y") instanceof Float)
                || !(data.get("creation_date") instanceof LocalDate)
                || !(data.get("from_x") instanceof Long)
                || !(data.get("from_y") instanceof Integer)
                || !(data.get("from_name") instanceof String)
                || !(data.get("to_x") instanceof Long)
                || !(data.get("to_y") instanceof Integer)
                || !(data.get("to_name") instanceof String)
                || !(data.get("distance") instanceof Integer)) {
            return false;
        }

        return true;
    }

    @Override
    public String process(Map<String, Object> context) {
        Route route = new Route();
        route.setName((String) context.get("name"));
        route.setCoordinates(
                new Coordinates(
                        (Long) context.get("coord_x"),
                        (Float) context.get("coord_y")
                )
        );
        route.setCreationDate((LocalDate) context.get("creation_date"));
        route.setFrom(
                new Location(
                        (Long) context.get("from_x"),
                        (Integer) context.get("from_y"),
                        (String) context.get("from_name")
                )
        );
        route.setTo(
                new Location(
                        (Long) context.get("to_x"),
                        (Integer) context.get("to_y"),
                        (String) context.get("to_name")
                )
        );
        route.setDistance((Integer) context.get("distance"));

        Route[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));

        int count = 0;
        for (Route i : arr) {
            if (route.compareTo(i) > 0) {
                data.getStorage().remove(i);
                count++;
            }
        }
        return count == 0 ? "None of elements were deleted" : "Deleted " + count + " element(s)";
    }
}

package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;

import java.util.Map;
import java.util.Objects;

/**
 * The type main.lab6.Command count by distance.
 */
public class CommandCountByDistance extends Command implements RemoteCommand {
    private final Integer distance;

    /**
     * Instantiates a new main.lab6.Command count by distance.
     *
     * @param data     the data
     * @param distance the distance
     */
    public CommandCountByDistance(DeqCollection<Route> data, Integer distance) {
        super(data);
        this.distance = distance;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        int count = 0;
        for (T i : data.getStorage().toArray(data.createContentsArray(data.getStorage().size()))) {
            if (Objects.equals(i.getDistance(), this.distance)) count++;
        }
        System.out.println(count);
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
        if (!data.containsKey("distance")) {
            return false;
        }
        return data.get("distance") instanceof Integer;
    }

    @Override
    public ResponseEntity process(Map<String, Object> context) {
        int count = 0;
        for (Route i : data.getStorage().toArray(data.createContentsArray(data.getStorage().size()))) {
            if (Objects.equals(i.getDistance(), context.get("distance"))) {
                count++;
            }
        }
        return new ResponseEntity(String.valueOf(count));
    }
}

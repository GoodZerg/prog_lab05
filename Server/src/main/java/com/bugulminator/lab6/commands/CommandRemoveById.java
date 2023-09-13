package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;

import java.util.Map;

/**
 * The type main.lab6.Command remove by id.
 */
public class CommandRemoveById extends Command implements RemoteCommand {
    private final long id;

    /**
     * Instantiates a new main.lab6.Command remove by id.
     *
     * @param data the data
     * @param id   the id
     */
    public CommandRemoveById(DeqCollection<Route> data, Long id) {
        super(data);
        this.id = id;
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        for (T i : arr) {
            if (i.getId() == id) {
                data.getStorage().remove(i);
                return;
            }
        }
        System.out.println("Такого id нет");
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
        if (!data.containsKey("id")) {
            return false;
        }
        return data.get("id") instanceof Integer;
    }

    @Override
    public String process(Map<String, Object> context) {
        Route[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        for (Route i : arr) {
            if (i.getId() == id) {
                data.getStorage().remove(i);
                return "Successfully deleted";
            }
        }
        return "No element with such id";
    }
}

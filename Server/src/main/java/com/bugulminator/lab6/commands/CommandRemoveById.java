package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DatabaseManager;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;
import com.bugulminator.lab6.network.ResponseStatus;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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
        this.id = id != null ? id : 0;
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
    public ResponseEntity process(Map<String, Object> context, String executor) {
        Route[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        int id = (int) context.get("id");
        for (Route it : arr) {
            if (it.getId() == id  && Objects.equals(it.getOwner(), executor)) {
                try {
                    DatabaseManager.removeRoute(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                data.getStorage().remove(it);
                return new ResponseEntity("Successfully deleted");
            }
        }
        return new ResponseEntity("No element with such id", ResponseStatus.ERROR);
    }
}

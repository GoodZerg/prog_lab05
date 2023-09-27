package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The type main.lab6.Command print field descending distance.
 */
public class CommandPrintFieldDescendingDistance extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command print field descending distance.
     *
     * @param data the data
     */
    public CommandPrintFieldDescendingDistance(DeqCollection<Route> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        T[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        Arrays.sort(arr, T::compareTo);
        List<T> list = Arrays.asList(arr);
        Collections.reverse(list);
        for (T i : list) {
            System.out.println(i.getDistance());
        }
    }

    @Override
    public void execute() {
        fooHelper(data);
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        return data == null;
    }

    @Override
    public ResponseEntity process(Map<String, Object> context, String executor) {
        StringBuilder res = new StringBuilder();
        Route[] arr = data.getStorage().toArray(data.createContentsArray(data.getStorage().size()));
        Arrays.sort(arr, Route::compareTo);
        List<Route> list = Arrays.asList(arr);
        Collections.reverse(list);
        for (Route i : list) {
            res.append(i.getDistance()).append("\n");
        }
        return new ResponseEntity(res.toString());
    }
}

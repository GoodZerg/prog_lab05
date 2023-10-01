package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;

import java.util.Map;

/**
 * The type main.lab6.Command show.
 */
public class CommandShow extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command show.
     *
     * @param data the data
     */
    public CommandShow(DeqCollection<Route> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        for (Collectible i : data.getStorage()) {
            System.out.println(i.toString());
        }
    }

    @Override
    public void execute() {
        System.out.println("Collection: \n");
        fooHelper(data);
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        return data == null;
    }

    @Override
    public ResponseEntity process(Map<String, Object> context, String executor) {
        DeqCollection.rLock.lock();
        StringBuilder res = new StringBuilder();
        res.append("Collection: \n");
        for (Collectible i : data.getStorage()) {
            res.append(i.toString());
        }
        DeqCollection.rLock.unlock();
        return new ResponseEntity(res.toString());
    }
}

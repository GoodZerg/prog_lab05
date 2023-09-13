package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;

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
    public String process(Map<String, Object> context) {
        String res = "";
        res += ("Collection: \n");
        for (Collectible i : data.getStorage()) {
            res += (i.toString());
        }
        return res;
    }
}

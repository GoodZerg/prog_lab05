package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.Collectible;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * The type main.lab6.Command max by coordinates.
 */
public class CommandMaxByCoordinates extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command max by coordinates.
     *
     * @param data the data
     */
    public CommandMaxByCoordinates(DeqCollection<Route> data) {
        super(data);
    }

    private <T extends Collectible & Comparable<T>> void fooHelper(DeqCollection<T> data) {
        Optional<T> max = data.findMaxByCord();
        max.ifPresent(System.out::println);
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
    public String process(Map<String, Object> context) {
        Optional<Route> max = data.findMaxByCord();
        final String res[] = new String[1];
        Consumer<? super Route> resFiller = (Route route) -> {
            res[0] = route.toString();
        };
        max.ifPresent(resFiller);
        return res[0];
    }
}

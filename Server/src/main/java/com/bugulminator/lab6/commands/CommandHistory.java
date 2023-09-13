package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.Invoker;
import com.bugulminator.lab6.command.RemoteCommand;

import java.util.Map;
import java.util.Vector;

import static java.lang.Math.max;

/**
 * The type main.lab6.Command history.
 */
public class CommandHistory extends Command implements RemoteCommand {
    private static final int max_history = 6;

    /**
     * Instantiates a new main.lab6.Command history.
     *
     * @param data the data
     */
    public CommandHistory(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        Vector<Command> history = Invoker.getDoneCommands();
        if (history.isEmpty()) {
            System.out.println("History is empty");
        }
        int sizeHistory = history.size();
        for (int i = sizeHistory - 1; i >= max(sizeHistory - max_history, 0); i--) {
            Invoker.CommandInformation command = Invoker.findCommandByClass(history.get(i).getClass());
            System.out.println(i + 1 + ": " + command.name);
        }
        System.out.println("...");
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        return data == null;
    }

    @Override
    public String process(Map<String, Object> context) {
        String res = "";
        Vector<Command> history = Invoker.getDoneCommands();
        if (history.isEmpty()) {
            res += "History is empty\n";
        }
        int sizeHistory = history.size();
        for (int i = sizeHistory - 1; i >= max(sizeHistory - max_history, 0); i--) {
            Invoker.CommandInformation command = Invoker.findCommandByClass(history.get(i).getClass());
            res += (i + 1 + ": " + command.name + "\n");
        }
        res += ("...");
        return res;
    }
}
package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;

import java.util.Map;

/**
 * The type main.lab6.Command info.
 */
public class CommandInfo extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command info.
     *
     * @param data the data
     */
    public CommandInfo(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        System.out.println("Type: " + data.getStorage().getClass());
        System.out.println("Creation date: " + data.getCreationDate());
        System.out.println("Number of elements: " + data.getStorage().size());
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        return data == null;
    }

    @Override
    public ResponseEntity process(Map<String, Object> context) {
        String res = "";
        res += "type : " + data.getStorage().getClass() + "\n";
        res += "creationDate : " + data.getCreationDate() + "\n";
        res += "number of elements : " + data.getStorage().size() + "\n";
        return new ResponseEntity(res);
    }
}

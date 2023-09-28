package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DatabaseManager;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.RemoteCommand;
import com.bugulminator.lab6.command.ResponseEntity;
import com.bugulminator.lab6.network.Credentials;
import com.bugulminator.lab6.network.ResponseStatus;

import java.util.Map;

public class CommandRegister extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command.
     *
     * @param data the data
     */
    public CommandRegister(DeqCollection<Route> data) {
        super(data);
    }

    @Override
    public void execute() {
        System.err.println("This command is not executable on server");
    }

    @Override
    public boolean isValid(Map<String, Object> data) {
        if (data == null) {
            return false;
        }
        if (!data.containsKey("credentials")) {
            return false;
        }
        return data.get("credentials") instanceof Credentials;
    }

    @Override
    public ResponseEntity process(Map<String, Object> context, String executor) {
        Credentials credentials = (Credentials) context.get("credentials");
        boolean success = DatabaseManager.register(credentials.login(), credentials.password());
        return success ?
                new ResponseEntity("Success")
                :
                new ResponseEntity("Login is taken", ResponseStatus.ERROR);
    }
}

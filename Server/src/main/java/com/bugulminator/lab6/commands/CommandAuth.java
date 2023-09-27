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

public class CommandAuth extends Command implements RemoteCommand {
    /**
     * Instantiates a new main.lab6.Command.
     *
     * @param data the data
     */
    public CommandAuth(DeqCollection<Route> data) {
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
    public ResponseEntity process(Map<String, Object> context) {
        Credentials credentials = (Credentials) context.get("credentials");
        boolean success = DatabaseManager.auth(credentials.login(), credentials.password());
        return success ?
                new ResponseEntity("Success")
                :
                new ResponseEntity("Incorrect credentials", ResponseStatus.ERROR);
    }
}

package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.AuthHandler;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.network.Credentials;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class CredentialCommand extends Command {
    protected final BufferedReader reader;
    protected final boolean isStandardInput;

    /**
     * Instantiates a new main.lab6.Command.
     *
     * @param data the data
     */
    public CredentialCommand(DeqCollection<?> data, BufferedReader reader, boolean isStandardInput) {
        super(data);
        this.reader = reader;
        this.isStandardInput = isStandardInput;
    }

    protected void requestCredentials() {
        String login = "";
        String password = "";

        while (login.length() == 0) {
            try {
                System.out.print("Login: ");
                login = reader.readLine().trim();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                break;
            }
        }

        while (password.length() == 0) {
            try {
                System.out.print("Password: ");
                password = reader.readLine().trim();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                break;
            }
        }

        AuthHandler.getInstance().setLogin(login);
        AuthHandler.getInstance().setPassword(password);
    }
}

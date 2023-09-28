package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.AuthHandler;
import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.exceptions.NotAuthorizedException;
import com.bugulminator.lab6.network.C2SPackage;
import com.bugulminator.lab6.network.ResponseStatus;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class CommandAuth extends CredentialCommand {
    /**
     * Instantiates a new main.lab6.Command.
     *
     * @param data            the data
     * @param reader
     * @param isStandardInput
     */
    public CommandAuth(DeqCollection<?> data, BufferedReader reader, boolean isStandardInput) {
        super(data, reader, isStandardInput);
    }

    @Override
    public void execute() throws NotAuthorizedException {
        requestCredentials();

        Map<String, Object> context = new HashMap<>();
        context.put("credentials", AuthHandler.getInstance().getCredentials());

        NetworkHandler.getInstance().sendPackage(
                new C2SPackage(
                        this.getClass(),
                        context,
                        AuthHandler.getInstance().getCredentials()
                )
        );

        if (NetworkHandler.getInstance().waitForResponseStatus() != ResponseStatus.OK) {
            AuthHandler.getInstance().resetCredentials();
        }
    }
}

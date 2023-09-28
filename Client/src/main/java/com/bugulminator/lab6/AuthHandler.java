package com.bugulminator.lab6;

import com.bugulminator.lab6.exceptions.NotAuthorizedException;
import com.bugulminator.lab6.network.Credentials;

public class AuthHandler {
    private AuthHandler() {}

    private static AuthHandler instance = null;
    private String login = null;
    private String password = null;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void resetCredentials() {
        login = null;
        password = null;
    }

    public Credentials getCredentials() throws NotAuthorizedException {
        if (login == null) {
            throw new NotAuthorizedException("Not authenticated");
        }
        return new Credentials(login, password);
    }

    public static AuthHandler getInstance() {
        if (instance == null) {
            instance = new AuthHandler();
        }
        return instance;
    }
}

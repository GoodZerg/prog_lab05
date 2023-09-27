package com.bugulminator.lab6.network;

import java.io.Serializable;

public record Credentials(
        String login,
        String password
) implements Serializable {}

package com.bugulminator.lab6.network;

import java.io.Serializable;

public record S2CPackage(
        String response,
        ResponseStatus status
) implements Serializable {}

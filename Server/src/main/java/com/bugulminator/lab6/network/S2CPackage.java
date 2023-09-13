package com.bugulminator.lab6.network;

import java.io.Serializable;

public record S2CPackage(
        String response
) implements Serializable {}

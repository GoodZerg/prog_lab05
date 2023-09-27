package com.bugulminator.lab6.network;

import com.bugulminator.lab6.command.Command;

import java.io.Serializable;
import java.util.Map;

public record C2SPackage(
        Class<? extends Command> clazz,
        Map<String, Object> data,
        Credentials credentials
) implements Serializable {}

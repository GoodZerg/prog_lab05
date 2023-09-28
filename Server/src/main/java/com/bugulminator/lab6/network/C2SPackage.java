package com.bugulminator.lab6.network;

import java.io.Serializable;
import java.util.Map;

public record C2SPackage(
        Class<?> clazz,
        Map<String, Object> data,
        Credentials credentials
) implements Serializable {}

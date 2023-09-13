package com.bugulminator.lab6.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public record C2SPackage(
        Class<?> clazz,
        Map<String, Object> data
) implements Serializable {}

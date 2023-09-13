package com.bugulminator.lab6.command;

import java.util.Map;

public interface RemoteCommand {
    boolean isValid(Map<String, Object> data);

    String process(Map<String, Object> context);
}

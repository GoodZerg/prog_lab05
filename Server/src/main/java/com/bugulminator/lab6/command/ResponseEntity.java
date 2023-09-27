package com.bugulminator.lab6.command;

import com.bugulminator.lab6.network.ResponseStatus;

public record ResponseEntity(
        String output,
        ResponseStatus status
) {
    public ResponseEntity(String output) {
        this(output, ResponseStatus.OK);
    }

    public ResponseEntity(ResponseStatus status) {
        this("$%NO_OUTPUT%$", status);
    }
}

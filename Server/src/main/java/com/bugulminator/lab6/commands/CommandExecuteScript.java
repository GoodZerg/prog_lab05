package com.bugulminator.lab6.commands;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.command.Command;
import com.bugulminator.lab6.command.Invoker;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Vector;

/**
 * The type main.lab6.Command execute script.
 */
public class CommandExecuteScript extends Command {
    private final String fileName;

    /**
     * Instantiates a new main.lab6.Command execute script.
     *
     * @param data     the data
     * @param fileName the file name
     */
    public CommandExecuteScript(DeqCollection<Route> data, String fileName) {
        super(data);
        this.fileName = fileName;
    }

    private boolean checkRecursion(BufferedReader reader, Vector<String> callStack) {
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                String[] words = line.split(" ");
                if (Objects.equals(words[0], "execute_script")) {
                    for (String i : callStack) {
                        if (Objects.equals(i, words[1])) {
                            return false;
                        }
                    }
                    callStack.add(words[1]);
                    if (checkRecursion(readFile(words[1]), callStack)) {
                        callStack.remove(words[1]);
                    } else {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read the file");
        }
        return true;
    }

    /**
     * Read file buffered reader.
     *
     * @param name the file name
     * @return the buffered reader
     * @throws FileNotFoundException the file not found exception
     */
    public BufferedReader readFile(String name) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(name)));
    }


    @Override
    public void execute() {
        BufferedReader reader;
        try {
            reader = readFile(fileName);
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Cannot open file");
            return;
        }
        Vector<String> callStack = new Vector<>(1);
        callStack.add(fileName);
        if (checkRecursion(reader, callStack)) {
            try {
                reader = readFile(fileName);
            } catch (FileNotFoundException | NullPointerException e) {
                System.out.println("Cannot open file");
                return;
            }
            Invoker localScriptInvoker = Invoker.createInstance(data);
            try {
                while (reader.ready()) {
                    try {
                        localScriptInvoker.parseCommand(reader.readLine(), reader, false);
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                             IllegalAccessException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                System.out.println("Cannot read the file");
            }
        } else {
            System.out.println("recursion");
        }

    }
}

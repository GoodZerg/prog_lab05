package com.bugulminator.lab6.command;

import com.bugulminator.lab6.NetworkHandler;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.commands.*;
import com.bugulminator.lab6.network.C2SPackage;
import com.bugulminator.lab6.network.ResponseStatus;
import com.bugulminator.lab6.network.S2CPackage;

import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

/**
 * The type main.lab6.Invoker.
 */
public class Invoker {
    private final DeqCollection<?> data;
    private static final Vector<Command> doneCommands = new Vector<>();

    /**
     * The constant command_num.
     */
    public static final int commandAmount = 16;

    /**
     * Get done commands vector.
     *
     * @return the vector
     */
    public static Vector<Command> getDoneCommands() {
        return doneCommands;
    }

    /**
     * The type main.lab6.Command information.
     */
    public static class CommandInformation {
        /**
         * The Name.
         */
        public String name;
        /**
         * The main.lab6.Command class.
         */
        public Class<?> commandClass;
        /**
         * The Argument count.
         */
        public int argument_count;

        /**
         * Instantiates a new main.lab6.Command information.
         *
         * @param name          the name
         * @param commandClass  the command class
         * @param argumentCount the argument count
         */
        CommandInformation(String name, Class<?> commandClass, int argumentCount) {
            this.name = name;
            this.commandClass = commandClass;
            this.argument_count = argumentCount;
        }

    }

    /**
     * The Commands info.
     */
    public static Vector<CommandInformation> commandsInfo;

    static {
        commandsInfo = new Vector<>(commandAmount);
        commandsInfo.add(new CommandInformation(
                "help",
                CommandHelp.class,
                0));
        commandsInfo.add(new CommandInformation(
                "info",
                CommandInfo.class,
                0));
        commandsInfo.add(new CommandInformation(
                "show",
                CommandShow.class,
                0));
        commandsInfo.add(new CommandInformation(
                "add",
                CommandAdd.class,
                0));
        commandsInfo.add(new CommandInformation(
                "check_id",
                CommandCheckId.class,
                0));
        commandsInfo.add(new CommandInformation(
                "update",
                CommandUpdate.class,
                1));
        commandsInfo.add(new CommandInformation(
                "remove_by_id",
                CommandRemoveById.class,
                1));
        commandsInfo.add(new CommandInformation(
                "clear",
                CommandClear.class,
                0));
        commandsInfo.add(new CommandInformation(
                "save",
                CommandSave.class,
                0));
        commandsInfo.add(new CommandInformation(
                "execute_script",
                CommandExecuteScript.class,
                1));
        commandsInfo.add(new CommandInformation(
                "exit",
                CommandExit.class,
                0));
        commandsInfo.add(new CommandInformation(
                "auth",
                CommandAuth.class,
                0));
        commandsInfo.add(new CommandInformation(
                "register",
                CommandRegister.class,
                0));
        commandsInfo.add(new CommandInformation(
                "add_if_max",
                CommandAddIfMax.class,
                0));
        commandsInfo.add(new CommandInformation(
                "remove_lower",
                CommandRemoveLower.class,
                0));
        commandsInfo.add(new CommandInformation(
                "history",
                CommandHistory.class,
                0));
        commandsInfo.add(new CommandInformation(
                "max_by_coordinates",
                CommandMaxByCoordinates.class,
                0));
        commandsInfo.add(new CommandInformation(
                "count_by_distance",
                CommandCountByDistance.class,
                1));
        commandsInfo.add(new CommandInformation(
                "print_field_descending_distance",
                CommandPrintFieldDescendingDistance.class,
                0));
    }

    /**
     * Find command by class command information.
     *
     * @param commandClass the command class
     * @return the command information
     */
    public static CommandInformation findCommandByClass(Class<?> commandClass) {
        for (CommandInformation i : commandsInfo) {
            if (commandClass == i.commandClass) return i;
        }
        return new CommandInformation("Error", Command.class, 0);
    }

    public void processRemoteRequest(C2SPackage data, SocketChannel remote) {
        ResponseEntity res;

        CommandInformation command = findCommandByClass(data.clazz());
        try {
            res = executeRemoteCommand(command.name, data.data());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException ex) {
            res = new ResponseEntity("Error while processing request\n" + ex.getMessage(), ResponseStatus.ERROR);
        }

        NetworkHandler.getInstance().sendPackage(
                new S2CPackage(res.output(), res.status()),
                remote
        );
    }

    private ResponseEntity executeRemoteCommand(String command, Map<String, Object> context)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (CommandInformation i : commandsInfo) {
            if (command.equals(i.name)) {
                if (Objects.equals("add", i.name) ||
                        Objects.equals("add_if_max", i.name) ||
                        Objects.equals("remove_lower", i.name)) {
                    return remoteExecute((Command) i.commandClass.getDeclaredConstructor(
                            DeqCollection.class, BufferedReader.class, boolean.class
                    ).newInstance(data, null, false), context);

                } else if (Objects.equals("check_id", i.name)) {
                    return remoteExecute((Command) i.commandClass.getDeclaredConstructor(
                            DeqCollection.class).newInstance(data), context);
                } else if (Objects.equals("update", i.name)) {
                    return remoteExecute((Command) i.commandClass.getDeclaredConstructor(
                                    DeqCollection.class, Long.class, BufferedReader.class, boolean.class)
                            .newInstance(data, null, null, false), context);

                } else if (Objects.equals("remove_by_id", i.name)) {
                    return remoteExecute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class, Long.class)
                            .newInstance(data, null), context);

                } else if (Objects.equals("count_by_distance", i.name)) {
                    return remoteExecute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class, Integer.class)
                            .newInstance(data, null), context);

                } else {
                    return remoteExecute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class)
                            .newInstance(data), context);
                }
            }
        }
        throw new IllegalArgumentException("wrong command");
    }

    private void findCommandByName(String[] words, BufferedReader reader, boolean isStandardInput)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String first_word = words[0];
        for (CommandInformation i :
                commandsInfo) {
            if (Objects.equals(first_word, i.name)) {
                if (words.length == i.argument_count + 1) {
                    if (Objects.equals("add", i.name) ||
                            Objects.equals("add_if_max", i.name) ||
                            Objects.equals("remove_lower", i.name)) {
                        execute((Command) i.commandClass.getDeclaredConstructor(
                                DeqCollection.class, BufferedReader.class, boolean.class
                        ).newInstance(data, reader, isStandardInput));
                        return;
                    } else if (words.length == 1) {
                        execute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class)
                                .newInstance(data));
                        return;
                    } else if (Objects.equals("update", i.name)) {
                        execute((Command) i.commandClass.getDeclaredConstructor(
                                        DeqCollection.class, Long.class, BufferedReader.class, boolean.class)
                                .newInstance(data, Long.parseLong(words[1]), reader, isStandardInput));
                        return;
                    } else if (Objects.equals("remove_by_id", i.name)) {
                        execute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class, Long.class)
                                .newInstance(data, Long.parseLong(words[1])));
                        return;
                    } else if (Objects.equals("count_by_distance", i.name)) {
                        execute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class, Integer.class)
                                .newInstance(data, Integer.parseInt(words[1])));
                        return;
                    } else if (Objects.equals("execute_script", i.name)) {
                        execute((Command) i.commandClass.getDeclaredConstructor(DeqCollection.class, String.class)
                                .newInstance(data, words[1]));
                        return;
                    }
                } else {
                    throw new IllegalArgumentException("wrong command args");
                }
            }
        }
        throw new IllegalArgumentException("wrong command");
    }

    /**
     * Execute.
     *
     * @param command the command
     */
    public void execute(Command command) {
        doneCommands.add(command);
        command.execute();
    }

    public static ResponseEntity remoteExecute(Command command, Map<String, Object> data) throws IllegalAccessException {
        doneCommands.add(command);
        if (command instanceof RemoteCommand remoteCommand) {
            if (!remoteCommand.isValid(data)) {
                throw new IllegalArgumentException("Illegal set of arguments for command");
            }
            return remoteCommand.process(data);
        } else {
            throw new IllegalAccessException("Command is not remote executable");
        }
    }

    /**
     * Instantiates a new main.lab6.Invoker.
     *
     * @param data the data
     */
    private Invoker(DeqCollection<?> data) {
        this.data = data;
    }

    private static Invoker instance = null;

    public static Invoker createInstance(DeqCollection<?> data) {
        if (instance == null) {
            instance = new Invoker(data);
        }
        return instance;
    }

    public static Invoker getInstance() {
        return instance;
    }

    /**
     * Parse command.
     *
     * @param str             the str
     * @param reader          the reader
     * @param isStandardInput the is standard input
     * @throws InvocationTargetException the invocation target exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws InstantiationException    the instantiation exception
     * @throws IllegalAccessException    the illegal access exception
     */
    public void parseCommand(String str, BufferedReader reader, boolean isStandardInput)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (str == null)
            throw new RuntimeException("null command");
        String[] words = str.trim().split(" ");
        findCommandByName(words, reader, isStandardInput);
    }
}


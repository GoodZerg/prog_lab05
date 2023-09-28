package com.bugulminator.lab6;

import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.io.InputHandler;
import com.bugulminator.lab6.io.OutputHandler;

/**
 * The type main.lab6.Main.
 */
public class Client {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Need param");
            return;
        }
        String fileName = args[0];
        NetworkHandler networkHandler = null;
        try {
            while (networkHandler == null) {
                networkHandler = NetworkHandler.getInstance();
            }
            new Thread(networkHandler::start).start();
            InputHandler inputHandler = new InputHandler(
                    new DeqCollection<>(
                            Route::new,
                            Route[]::new,
                            new OutputHandler(fileName)
                    )
            );
            inputHandler.start(fileName);
            networkHandler.close();
        } catch (Throwable ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
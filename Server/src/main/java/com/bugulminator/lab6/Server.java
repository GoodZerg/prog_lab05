package com.bugulminator.lab6;

import com.bugulminator.lab6.collection.DatabaseManager;
import com.bugulminator.lab6.collection.DeqCollection;
import com.bugulminator.lab6.collection.data.Route;
import com.bugulminator.lab6.io.InputHandler;
import com.bugulminator.lab6.io.OutputHandler;

/**
 * The type main.lab6.Main.
 */
public class Server {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        NetworkHandler networkHandler;
        try {
            DatabaseManager.connect();
            networkHandler = NetworkHandler.getInstance();
            new Thread(networkHandler::start).start();
            var collection = new DeqCollection<>(
                    Route::new,
                    Route[]::new
            );
            DatabaseManager.load(collection);
            InputHandler inputHandler = new InputHandler(collection);
            inputHandler.start();
            DatabaseManager.close();
            networkHandler.close();
        } catch (Throwable ex) {
            System.err.println(ex.getMessage());
        }
    }
}
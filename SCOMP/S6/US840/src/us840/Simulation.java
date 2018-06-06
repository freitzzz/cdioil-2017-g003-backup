package us840;

/**
 *
 * @author
 */
public class Simulation {
    
    private final static int NUMBER_OF_CLIENTS = 5;

    public Simulation() {
        runSimulation();
    }

    private void runSimulation() {
        ReviewList sharedObject = new ReviewList();

        Thread serverThread = new Thread(new Server(sharedObject));
        serverThread.start();

        Thread[] clientThreads = new Thread[NUMBER_OF_CLIENTS];
        for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
            clientThreads[i] = new Thread(new Client(sharedObject));
            clientThreads[i].start();
        }
    }

}

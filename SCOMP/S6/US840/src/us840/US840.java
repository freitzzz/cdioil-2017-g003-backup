package us840;

/**
 * Main class that runs the simulation of a server concurrently receiving
 * reviews from several feedback devices using threads.
 *
 * @author Group 3 CDIO-IL 2DX
 */
public class US840 {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) {
        new Simulation();
    }

//    /**
//     * Runs the simulation of a server concurrently receiving reviews from
//     * several feedback devices
//     */
//    private static void runSimulation(List<String> reviewList) throws InterruptedException {
//        Thread serverThread = new Thread(serverRunnable(reviewList));
//        serverThread.start();
//        Thread[] clientThreads = new Thread[NUMBER_OF_REVIEWS];
//        for (int i = 0; i < NUMBER_OF_REVIEWS; i++) {
//            clientThreads[i] = new Thread(clientRunnable(reviewList));
//            clientThreads[i].start();
//        }
//
//        for (int j = 0; j < NUMBER_OF_REVIEWS; j++) {
//            clientThreads[j].join();
//        }
//    }
//    /**
//     * Creates a Runnable for the client threads
//     *
//     * @param reviewList object that is shared and must be synced between the
//     * client threads and the server thread
//     * @return Runnable that creates a review and adds it to a list
//     */
//    private static Runnable clientRunnable(List<String> reviewList) {
//        return () -> {
//            synchronized (reviewList) {
//                int reviewNumber = new Random().nextInt();
//                String newReview = "Review number " + reviewNumber;
//                System.out.println("Sending " + newReview + " to the server");
//                reviewList.add(newReview);
//                reviewList.notify();
//            }
//        };
//    }
//
//    /**
//     * Creates a Runnable for the server thread that prints out the review info
//     * sent by each client thread
//     *
//     * @param reviewList object that is shared and must be synced between the
//     * client threads and the server thread
//     * @return Runnable that prints out the reviews sent by the client threads
//     */
//    private static Runnable serverRunnable(List<String> reviewList) {
//        return () -> {
//            while (true) {
//                synchronized (reviewList) {
//                    try {
//                        while (reviewList.isEmpty()) {
//                            reviewList.wait();
//                        }
//                        String review = reviewList.remove(0);
//                        System.out.println("Received " + review);
//                    } catch (InterruptedException ex) {
//                        System.out.println(ex.getMessage());
//                    }
//                }
//            }
//        };
//    }
}

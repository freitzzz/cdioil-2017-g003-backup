package cdioil.backoffice.console.presentation;

import cdioil.backoffice.application.LoadAnswersController;

/**
 * UI Class
 *
 * Stress Tests the application by loading
 * hundreds of Survey Reviews on runtime
 */
public class LoadAnswersUI {

    /**
     * Controller field
     */
    private LoadAnswersController controller;

    /**
     * Instantiates the controller and starts
     */
    public LoadAnswersUI() {
        controller = new LoadAnswersController();
        start();
    }

    /**
     * Starts the stress test
     */
    private void start() {
        System.out.println("================================");
        System.out.println("Loading Application with Answers");
        System.out.println("================================");

        final long timeTook = controller.performStressTest();

        // Number of elements that were loaded to the application
        System.out.println("Finished Loading Answers");
        System.out.println("================================");
        System.out.println(String.format("Took %s ms", timeTook));
        System.out.println("Finished.");
        System.out.println("100000 Reviews added");
        System.out.println("================================");
    }
}

package us840;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class Client implements Runnable {

    private ReviewList reviewList;

    public Client(ReviewList reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public void run() {
        while (!reviewList.isFlag()) {
            int reviewNumber = new Random().nextInt();
            String newReview = "Review number " + reviewNumber;
            try {
                System.out.println("Client sending " + newReview);
                reviewList.addReview(newReview);
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

package us840;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class Server implements Runnable {

    private static final int TOTAL_REVIEWS_TO_END_SIMULATION = 20;
    
    private ReviewList reviewList;

    public Server(ReviewList reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public void run() {
        while (true) {
            try {
               /* String review = reviewList.getReviewList().get(reviewList.getListSize());
                System.out.println("Server received " + review);*/
                if(reviewList.getReviewList().size() >= TOTAL_REVIEWS_TO_END_SIMULATION){
                    reviewList.setFlag();
                    for(String review : reviewList.getReviewList()){
                        System.out.println("Server saved " + review);
                    }
                    System.out.println("Simulation over");
                    return;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

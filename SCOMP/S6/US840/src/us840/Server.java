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
               reviewList.waitPassively();
                for(int i=reviewList.getListSize();i<reviewList.getListRealSize();i++){
                    System.out.println(reviewList.getReviewList().get(i));
                }
                reviewList.setNewSize();
                if(reviewList.getReviewList().size() >= TOTAL_REVIEWS_TO_END_SIMULATION){
                    reviewList.setFlag();
                    System.out.println("Simulation over");
                    return;
                }
                reviewList.sendMore();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

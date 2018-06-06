package us840;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author
 */
public class ReviewList {
    
    private List<String> reviewList;
    private int listSize;
    private boolean flag;
    
    
    public ReviewList(){
        reviewList = new LinkedList<>();
        listSize = 0;
        flag = false;
    }
    
    public synchronized boolean isFlag(){
        return flag;
    }
    
    public synchronized void setFlag(){
        flag = true;
    }
    
    public synchronized int getListSize(){
        return listSize;
    }
    
    public synchronized List<String> getReviewList() throws InterruptedException{
        while(listSize == reviewList.size()){
            listSize++;
            wait();
        }
        return reviewList;
    }
    
    public synchronized void addReview(String review) throws InterruptedException{
        reviewList.add(review);
        notify();
    }
    
}

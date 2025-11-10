package src.sadhana;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

class Tokens{
    double count;
    long usedDate;
    public Tokens(double capacity){
        count = capacity;
        usedDate = 0;
    }
}


public class TokenRateLimiter extends RateLimiter {
    double reFillRate;
    Map<Integer, Tokens> userTokens;


    public TokenRateLimiter(int capacity, long windowSize){
        super(windowSize, capacity);

        userTokens = new ConcurrentHashMap<>();
        userLocks = new ConcurrentHashMap<>();

        this.reFillRate = (double) capacity /windowSize;

    }

    private void reFill(int userId){
        userTokens.putIfAbsent(userId, new Tokens(capacity));
        Tokens t = userTokens.get(userId);
        long now = System.currentTimeMillis();
        System.out.println("time now ="+now);

        double newTokens = ((double) (now - t.usedDate) /1000) *reFillRate;

        double count = Math.min(capacity , t.count + newTokens );
        System.out.println("new Tokens ="+ newTokens);

        t.usedDate = now;
        t.count = count;

    }

    @Override
     boolean canRequest(int userId) {
            reFill(userId);
            Tokens t = userTokens.get(userId);
            System.out.println("-------tokens= "+t.count);
            if(t.count>=1){
                t.count = t.count -1;
                return  true;
            }
            return false;
    }

}

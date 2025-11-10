package src.sadhana;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindow extends RateLimiter {
    HashMap<Integer, Queue<Long>> userRequests;

    public SlidingWindow(int capacity,long windowsize){
        super(windowsize, capacity);
        userRequests = new HashMap<>();

    }

    private Queue<Long> getRequestQueue(int userId, long now){
        Queue<Long> log = userRequests.computeIfAbsent(userId, k->new LinkedList<>());
        System.out.println("time="+now);

        while (!log.isEmpty() && log.peek() <= now - windowSize*1000){
            log.poll();
        }

        return log;
    }

    @Override
     boolean canRequest(int userId) {
        long now = System.currentTimeMillis();
        getRequestQueue(userId, now);
        Queue<Long> log = userRequests.get(userId);
        //System.out.println("----Log size is="+log.size());
        //System.out.println("capacity="+capacity);
        if(log.size() < capacity){
            log.add(now);
            return  true;
        }

        return false;
    }
}

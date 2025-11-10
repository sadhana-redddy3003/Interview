package src.sadhana;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public abstract class RateLimiter {
    long windowSize;
    int capacity;
    Map<Integer, ReentrantLock> userLocks;
    public  RateLimiter(long windowSize, int capacity){
        this.windowSize = windowSize;
        this.capacity = capacity;
        userLocks = new ConcurrentHashMap<>();
    }

    ReentrantLock getUserLock(int userId){
        return userLocks.computeIfAbsent(userId, k->new ReentrantLock());
    }

    public boolean allowRequest(int userId) {
        ReentrantLock lock = getUserLock(userId);
        lock.lock();
        try {
            return canRequest(userId);
        }
        finally {
            lock.unlock();
        }
    }
     abstract boolean canRequest(int userId);
}


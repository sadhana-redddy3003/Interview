package src.sadhana;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        System.out.println("Hello world!");
        RateLimiter limiter = new TokenRateLimiter(6, 2);
        //refill rate -s -o.5 per secong
        //capacity -6, 7 wrong

        //RateLimiter limiter = new SlidingWindow(6, 2);

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        Thread.sleep(1000);
        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

        Thread.sleep(1000);
        if(limiter.allowRequest(1)) System.out.println("alloed");
        else System.out.println("Nooo");

    }
}


package main.thread;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author locks
 * @date 2022-03-22 17:36
 */
public class ThreadDemo {
    public static final AtomicLong currentThread = new AtomicLong(-1L);
    private static final AtomicInteger refcount = new AtomicInteger(0);


    public static void main(String[] args) throws Exception{



        long id = Thread.currentThread().getId();
            System.out.println("the original thread id is " + currentThread.get());
        if(id!=currentThread.get() && currentThread.compareAndSet(-1L,id)){
            System.out.println("after modify the current thread id is " + currentThread.get());
        }


        new Thread(()->{

            long id1 = Thread.currentThread().getId();
            if(id1 != currentThread.get() && !currentThread.compareAndSet(-1L,id1)){
                System.out.println("multi-thread has ready modify this id " + id1);
            }else {
                System.out.println("current thread id is " + id1);
            }
            },"A").start();




    }
}

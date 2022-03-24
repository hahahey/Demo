package main.semaphore;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SemaphoreDemo {
  public static void main(String[] args) {

    Semaphore semaphore = new Semaphore(0);

    LinkedBlockingQueue<String> eventQueue = new LinkedBlockingQueue<String>(3);

    AtomicBoolean stopped = new AtomicBoolean(false);

    Thread thread1 =
        new Thread(
            () -> {
              try {
                semaphore.acquire();
                System.out.println("获取到信号量");

              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });

    Thread thread2 =
        new Thread(
            () -> {
              try {
                semaphore.acquire();
                System.out.println("获取到信号量");

              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });

    thread1.start();
    thread2.start();

           Thread dealThread =  new Thread(() -> {
                System.out.println("处理线程开始工作");
                while (true) {
                    try {
                        semaphore.acquire();
                        System.out.println("处理线程获取到信号量");

                        String poll = eventQueue.poll();
                        if (poll == null) {
                            if (!stopped.get()) {
                                throw new IllegalStateException("has been stopped");
                            }
                            return;
                        }
                        System.out.println("获取到了值 " + poll);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            dealThread.start();


            new Thread(() -> {
                try {
                    System.out.println("状态线程开始工作");
                    eventQueue.offer("first value");
                    eventQueue.offer("second value");
                    eventQueue.offer("third value");
                  boolean eventAdd =   eventQueue.offer("fourth value");

                  if(eventAdd){
                      System.out.println("queue is over size");
                  }

                    TimeUnit.SECONDS.sleep(5);
                    semaphore.release();
                    System.out.println("状态线程释放了信号量");
                    TimeUnit.SECONDS.sleep(3);

                    System.out.println("状态线程释放了一个空的信号量");
                    if(stopped.compareAndSet(false,true)){
                        semaphore.release();
                        semaphore.release();
                        semaphore.release();
                        dealThread.join();
                    }
                    System.out.println("状态线程结束");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();


            try {
               dealThread.join();

                System.out.println("main 线程执行结束");
            } catch ( Exception e) {
                e.printStackTrace();
            }

  }
}

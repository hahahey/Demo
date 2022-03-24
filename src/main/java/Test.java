import cn.hutool.core.thread.NamedThreadFactory;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author locks
 * @date 2021-11-08 19:03
 */
public class Test {

  public static void main(String[] args) {


    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,
            5,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            //Executors.defaultThreadFactory(),
            //new NamedThreadFactory("thread_lzk_",false),
            new ThreadFactory() {
              @Override
              public Thread newThread(Runnable r) {
                return new Thread("thread_lzk_");
              }
            },
    new ThreadPoolExecutor.AbortPolicy());

    for (int i = 0; i < 50; i++) {
    threadPoolExecutor.submit(() -> {
//      try {
//        TimeUnit.SECONDS.sleep(10);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
      System.out.println(Thread.currentThread().getName() + "   " );});
    }

    System.out.println("    ");
  }
}

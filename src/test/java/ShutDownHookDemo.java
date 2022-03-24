import main.jar.Teacher;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ShutDownHookDemo {
    public static void main(String[] args) {

        Runtime.getRuntime().traceMethodCalls(true);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("执行钩子方法");

        }));


        Thread mainThread = Thread.currentThread();

        System.out.println("start.............");

      Thread thread =   new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("current number is " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(i == 3){
                    try {
                        mainThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

            thread.start();

        try {

            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        System.out.println("stop................");

    }
}

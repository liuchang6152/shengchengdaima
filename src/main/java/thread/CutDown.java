package thread;

import java.util.concurrent.CountDownLatch;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 10:06 2020/10/27
 * @ Description：
 * @ Modified By：
 */
public class CutDown {
    public static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) {
        for (int i=0; i <100 ; i++) {
            int finalI=i;
            new Thread(()->{
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
                try {
                    countDownLatch.await();
                    System.out.println(finalI +"-----------------------------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

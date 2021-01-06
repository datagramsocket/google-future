package future.callback;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author: fcq
 * @create: 2021-01-05
 */
public class ListenableFutureDemo {

    /**
     * juc包下自带的Future功能
     * */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(() -> {
            try {
                System.out.println("task id:" + Thread.currentThread().getId());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "finish";
        });
        System.out.println("main thread id:" + Thread.currentThread().getId());
        String result = future.get();
    }

    /**
     * google listenableFuture
     * */
    @Test
    public void test2(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.out.println("main thread id ：" + Thread.currentThread().getId());
        /*ListenableFutureTask<String> listenableFutureTask = ListenableFutureTask.create(() -> {
            //Thread.sleep(5000);
            countDownLatch.countDown();
            return "success";
        });*/

        SettableFuture<String> settableFuture = SettableFuture.create();

        Futures.addCallback(settableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("on success thread id：" + Thread.currentThread().getId());
                System.out.println(result);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, Executors.newFixedThreadPool(2));

        settableFuture.set("success");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test3(){}
}

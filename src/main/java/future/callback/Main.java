package future.callback;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("main 线程id: " + Thread.currentThread().getId());
        SettableFuture<Task.Response> future = Task.doTask();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        AsyncCallbackTemplate.withCallBack(future,
                (response) -> {
                    System.out.println("处理response回调 线程id: " + Thread.currentThread().getId() + "  response:" + response.toString());

                }, (throwable) -> {}, executorService);

        //
        new Thread((() -> {
            System.out.println("set Future 线程id:" + Thread.currentThread().getId());
            future.set(new Task.Response("task response"));
        })).start();

        Thread.sleep(50);
        executorService.shutdown();
    }

}

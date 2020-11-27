package com.infinova.future.callback;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @Description: google Future
 * @Author:fcq
 * @Date:2020/11/27
 **/
public class AsyncCallbackTemplate {

    public static <T> void withCallBack(ListenableFuture<T> listenableFuture, Consumer<T> onSuccess, Consumer<Throwable> onFailure, Executor executor){
        FutureCallback futureCallback = new FutureCallback<T>() {
            public void onSuccess(@Nullable T t) {
                onSuccess.accept(t);
            }

            public void onFailure(Throwable throwable) {
                onFailure.accept(throwable);
            }
        };
        if(executor != null){
            Futures.addCallback(listenableFuture, futureCallback, executor);
        }else{
            Futures.addCallback(listenableFuture, futureCallback, MoreExecutors.directExecutor());
        }
    }

}

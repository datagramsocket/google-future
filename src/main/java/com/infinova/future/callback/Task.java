package com.infinova.future.callback;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

public class Task {


    public static  SettableFuture<Response>  doTask(){
        System.out.println("doTask 执行 线程id:" + Thread.currentThread().getId());
        SettableFuture<Response> future = SettableFuture.create();
        return future;
    }

    static class Response {
        Response(String msg){
            this.msg = msg;
        }

        public String msg;

        @Override
        public String toString() {
            return "Response{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }
}

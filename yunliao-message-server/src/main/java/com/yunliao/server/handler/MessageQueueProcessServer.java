package com.yunliao.server.handler;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MessageQueueProcessServer {

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 300, 2000, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(20));
    public static ConcurrentLinkedQueue searchRuterThreadQueue = new ConcurrentLinkedQueue();


    public static void start(int size) {
        if (size > 20) {
            size = 20;
        }
        for (int i = 0; i < size; i++) {
            MessageQueueProcessThread t = new MessageQueueProcessThread();
            searchRuterThreadQueue.add(t);
            executor.execute(t);
        }
    }

    public static void stop() {
        Iterator it = searchRuterThreadQueue.iterator();
        while (it.hasNext()) {
            MessageQueueProcessThread t = (MessageQueueProcessThread) it.next();
            t.stop();
        }
    }


}



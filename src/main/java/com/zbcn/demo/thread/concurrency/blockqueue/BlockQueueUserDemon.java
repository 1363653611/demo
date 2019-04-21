package com.zbcn.demo.thread.concurrency.blockqueue;

import java.util.concurrent.*;

/**
 *
 */
public class BlockQueueUserDemon {

    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue<String>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(producer);
        executor.execute(consumer);
        executor.shutdown();
    }
}

class Producer implements Runnable{

    private BlockingQueue<String> queue;

    public Producer(){};
    public Producer(BlockingQueue queue){
        this.queue = queue;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("producer produce 。。。 ");
        queue.add("product");
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(){};
    public Consumer(BlockingQueue queue){
        this.queue = queue;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            System.out.println("consumer waiting");
            String take = queue.take();
            System.out.println("consumer take ->"+ take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

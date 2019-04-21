package com.zbcn.demo.thread.concurrency.fockjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author zbcn8
 */
public class ForkJoinDemon extends RecursiveTask<Integer>{

    private final int threshold = 5;

    private int first;

    private int last;

    public ForkJoinDemon(int first, int last) {
        this.first = first;
        this.last = last;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Integer compute() {
        int result = 0;
        if(last -first <= threshold){
            //小于阈值，则直接计算
            for (int i = first; i < last; i++) {
                result += i;
            }

        }else {
            int midle = first + (last -first)/2;
            ForkJoinDemon left = new ForkJoinDemon(first, midle);

            ForkJoinDemon right = new ForkJoinDemon(midle + 1, last);
            left.fork();
            right.fork();
            result = left.join() + right.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinDemon demon = new ForkJoinDemon(1,1000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(demon);
        System.out.println(submit.get());
    }
}

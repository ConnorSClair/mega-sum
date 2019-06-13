package com.connor.hpc;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongArray;

public class QuickSolve extends Solve implements Runnable {
    ArrayList<Integer> data;
    int start;
    int stop;
    int threadId;
    AtomicLongArray results;

    public QuickSolve(ArrayList<Integer> data, int start, int stop, int threadId, AtomicLongArray results) {
        this.data = data;
        this.start = start;
        this.stop = stop;
        this.threadId = threadId;
        this.results = results;
    }

    public static long solver(ArrayList<Integer> data, int start, int stop) {
        long result = 0;
        for (int i = start; i < stop; i++) {
            result += solveNumber(data.get(i));
        }
        return result;
    }

    public void run() {
        long result = QuickSolve.solver(data, start, stop);
        System.out.println(String.format("%s %d", threadId, result));
        results.set(threadId, result);
    }

    /*
     *
     */
    public static long quickSolve(ArrayList<Integer> data, int threadCount) {
        long result = 0;
        // thread pool
        try {
            ExecutorService es = Executors.newCachedThreadPool();
            AtomicLongArray results = new AtomicLongArray(threadCount);
            for (int i = 0; i < threadCount; i++) {
                int start = i*data.size()/threadCount;
                int stop;
                if (i == threadCount - 1) {
                    stop = data.size();
                } else {
                    stop = (i+1)*data.size()/threadCount;
                }
                es.execute(new Thread(new QuickSolve(data,start,stop,i,results)));
            }
            es.shutdown();
            boolean finished = es.awaitTermination(30, TimeUnit.SECONDS);
            if (finished) {
                for (int i = 0; i < threadCount; i++) {
                    result += results.get(i);
                }
            }
            return result;
            /*boolean finished = es.awaitTermination(30, TimeUnit.SECONDS);
            if (!finished) {
                es.shutdownNow();
                System.exit(1);
            } else {
                System.out.println(String.format("Result is %d",result));
                return result;
            }*/
        } catch (Exception e) {
            System.out.println(String.format("%s", e.toString()));
            System.exit(2);
        }
        return result;
    }

}
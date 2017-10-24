package com.springapp.common.scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class JobContext {

    private static final int defaultResultSize = 100;

    /**
     * holder the result if the scheduled task
     */
    private LinkedBlockingQueue<Object> resultQueue = new LinkedBlockingQueue<Object>(
            defaultResultSize * 2);

    public Object waitForResult() {
        try {
            return resultQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> waitForResults() {
        List<Object> list = new LinkedList<Object>();
        resultQueue.drainTo(list);
        return list;
    }

    public void putResult(Object o) {
        if (resultQueue.remainingCapacity() < defaultResultSize) {
            resultQueue.remove();
        }
        resultQueue.add(o);
    }
}

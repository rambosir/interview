package org.example.base.task;

import lombok.RequiredArgsConstructor;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-28 14:57
 **/
@RequiredArgsConstructor
public class LoggingRunnable implements Runnable {

    private final Runnable runnable;

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("task started at " + startTime);
        runnable.run();
        System.out.println("task finished. Elapsed time:" + (System.currentTimeMillis() - startTime));

    }
}

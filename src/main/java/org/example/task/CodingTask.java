package org.example.task;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-28 14:58
 **/
public class CodingTask implements Runnable {
    @Override
    public void run() {
        System.out.println("write code.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

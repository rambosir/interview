package org.example.base.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolTest {
    
    public static void main(String[] args) {
        workStealingPool();
    }

    private static void workStealingPool() {
        // 创建工作窃取线程池
        ExecutorService executor = Executors.newWorkStealingPool();
        // 创建一批耗时的任务
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int taskNumber = i;
            Callable<String> task = () -> {
                // 模拟耗时操作
                Thread.sleep(1000);
                return "Task " + taskNumber + " executed by thread " + Thread.currentThread().getName();
            };
            tasks.add(task);
        }

        try {
            // 提交任务并获取执行结果
            List<Future<String>> results = executor.invokeAll(tasks);

            // 输出执行结果
            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 关闭线程池
        executor.shutdown();
    }
}


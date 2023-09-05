package org.example.base.task;

import lombok.RequiredArgsConstructor;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-28 14:57
 **/

@RequiredArgsConstructor
public class TransactionRunnable implements Runnable {

    private final Runnable runnable;

    @Override
    public void run() {
        boolean shouldRollback = false;
        try {
            beginTransaction();
            runnable.run();
        } catch (Exception ex) {
            shouldRollback = true;
            throw ex;
        } finally {
            if (shouldRollback) {
                rollback();
            } else {
                commit();
            }
        }


    }

    private void beginTransaction() {
        System.out.println("start transaction");
    }

    private void rollback() {
        System.out.println("rollback");
    }

    private void commit() {
        System.out.println("commit");
    }
}

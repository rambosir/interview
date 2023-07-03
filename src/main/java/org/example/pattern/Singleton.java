package org.example.pattern;

/**
 * @author Ben
 * @program interview
 * @date 2023-06-15 16:50
 **/
public class Singleton {

    private static volatile Singleton singleton = null;

    private Singleton() {
    }

    public static Singleton getSingleton() {
        // 尽量避免重复进入同步块
        if (singleton == null) {
            // 同步.class，意味着对同步类方法调用
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}

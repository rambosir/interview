package org.example.juc.jmm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-03 17:06
 */
public class JMMDemo {

  public static void main(String[] args) {

    /*
    JMM(Java内存模型Java Memory Model,简称JMM)本身是一种抽象的概念并不真实存在它仅仅描述的是一组约定或规范，
    通过这组规范定义了程序中(尤其是多线程)各个变量的读写访问方式并决定一个线程对共享变量的写入何时以及如何变成对另一个线程可见，
    关键技术点都是围绕多线程的原子性、可见性和有序性展开的。
    原则:
    JMM的关键技术点都是围绕多线程的原子性、可见性和有序性展开的
    能干嘛?
    1 通过JMM来实现线程和主内存之间的抽象关系。
    2 屏蔽各个硬件平台和操作系统的内存访问差异以实现让Java程序在各种平台下都能达到一致的内存访问效果。
    */

    /*
    我们定义的所有共享变量都储存在物理主内存中
    每个线程都有自己独立的工作内存，里面保存该线程使用到的变量的副本(主内存中该变量的一份拷贝)
    线程对共享变量所有的操作都必须先在线程自己的工作内存中进行后，写回主内存，不能直接从主内存中读写(不能越级)
    不同线程之间也无法直接访问其他线程的工作内存中的变量，线程间变量值的传递需要通过主内存来进行(同级不能相互访间)
    */

    /*
    JMM定义了线程和主内存之间的抽象关系
    1 线程之间的共享变量存储在主内存中(从硬件角度来说就是内存条)
    2 每个线程都有一个私有的本地工作内存，本地工作内存中存储了该线程用来读/写共享变量的副本(从硬件角度来说就是CPU的缓存
    ，比如寄存器、L1、L2、L3缓存等)
    */

    // JMM规范
    // happens-before 理念
    /*
         在JMM中，
         如果一个操作执行的结果需要对另一个操作可见性
         或者代码重排序，那么这两个操作之间必须存在happens-before关系。

         happens-before (先行发生)
         如果Java内存模型中所有的有序性都仅靠volatile和synchronized来完成，那么有很多操作都将会变得非常啰嗦，
         但是我们在编写Java并发代码的时候并没有察觉到这一点。

         我们没有时时、处处、次次，添加volatile和synchronized来完成程序，这是因为Java语言中JMM原则下
         有一个“先行发生”(Happens-Before)的原则限制和规矩

         这个原则非常重要:
         它是判断数据是否存在竞争，线程是否安全的非常有用的手段。依赖这个原则，我们可以通过几条简单规则一揽子解决并发环境下两个操
         作之间是否可能存在冲突的所有问题，而不需要陷入Java内存模型苦涩难懂的底层编译原理之中。

         happens-before总原则
         如果一个操作happens-before另一个操作，那么第一个操作的执行结果将对第二个操作可见，
         而且第一个操作的执行顺序排在第二个操作之前。
         两个操作之间存在happens-before关系，并不意味着一定要按照happens-before原则制定的顺序来执行。
         如果重排序之后的执行结果与按照happens-before关系来执行的结果一致，那么这种重排序并不非法。
    */

    /*
        内存屏障(也称内存栅栏，内存栅障，屏障指令等，是一类同步屏障指令，是CPU或编译器在对内存随机访问的操作中的一个同步点，使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作)，
        避免代码重排序。内存屏障其实就是一种JVM指令,
        Java内存模型的重排规则会要求Java编译器在生成JVM指令时插入特定的内存屏障指令，通过这些内存屏障指令，volatile实现了
        Java内存模型中的可见性和有序性，但volatile无法保证原子性。

        内存屏障之前的所有写操作都要回写到主内存
        内存屏障之后的所有读操作都能获得内存屏障之前的所有写操作的最新结果(实现了可见性)。

        因此重排序时，不允许把内存屏障之后的指令重排序到内存屏障之前。
        一句话:对一个volatile域的写, happens-before于任意后续对这个volatile域的读，也叫写后读。
        */

    // volatile 可见、有序性
    List<Integer> list = new ArrayList<>();
    List<Integer> list2 = new CopyOnWriteArrayList<>();

    IntStream.range(0, 10000).forEach(list::add);
    IntStream.range(0, 10000).parallel().forEach(list2::add);
    System.out.println(list.size());
    System.out.println(list2.size());
  }
}

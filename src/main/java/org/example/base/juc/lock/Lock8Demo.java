package org.example.base.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-02 11:58
 *     <p>1 标准访问有ab两个线程，请问先打印邮件还是短信
 *     <p>2 sendEmail方法等待3s，请问先打印邮件还是短信
 *     <p>一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *     其它的线程都只能等待，换句话说，某个时刻内，只能有唯一的个线程去访问这些synchronized方法
 *     锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *     <p>
 *     <p>3 新增一个普通的hello方法，请问先打印邮件还是短信
 *     <p>4 有2部手机，请问先打印邮件还是短信
 *     <p>加个普通方法后发现和同步锁无关, helLo换成两个对象后，不是同一把锁了，情况立刻交化。
 *     <p>
 *     <p>5 两个静态同步方法，同一部手机，请问先打印邮件还是短信
 *     <p>6 两个静态同步方法，2部手机，请问先打印邮件还是短信
 *     <p>都换成静态同步方法后，情况又变化 三种synchronized 锁的内容有一些差别:
 *     对于普通同步方法，锁的是当前实例对象，通常指this,具体的一部部手机,所有的普通同步方法用的都是同一把锁-实例对象本身，
 *     对于静态同步方法，锁的是当前类的Class对象，如phone.class唯一的一个模板，对于同步方法块，锁的是synchronized号内的对象
 *     <p>
 *     <p>7 1个静态同步方法，1个普通同步方法，同1部手机，请问先打印邮件还是短信
 *     <p>8 1个静态同步方法，1个普通同步方法，2部手机，请问先打印邮件还是短信
 *     <p>当一个线程试图访问同步代码时它首先必须得到锁，退出或抛出异常时必须释放锁。。。 所有的普通同步方法用的都是同一把锁（实例对象本身，就是new出来的具体实例对象本身，本类this）
 *     也就是说如果一个实例对象的通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释成锁后才能获取锁。
 *     所有的静态同步方法用的也是同一把锁（类对象本身，就是我们说过的唯一模板Class）
 *     具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的。
 *     但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
 */
public class Lock8Demo {

  public static void main(String[] args) throws InterruptedException {
    // 资源对象1
    Phone phone = new Phone();
    // 资源对象2
    Phone phone2 = new Phone();

    new Thread(
            () -> {
              phone.sendEmail();
            },
            "a")
        .start();
    TimeUnit.MILLISECONDS.sleep(200);
    new Thread(
            () -> {
              //              phone.sendSMS();
              //              phone.hello();
              phone2.sendSMS();
            },
            "b")
        .start();
  }
}

class Phone {

  public synchronized void sendEmail() {
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("-------sendEmail");
  }

  public synchronized void sendSMS() {
    System.out.println("-----sendSMS");
  }

  public void hello() {
    System.out.println("-----hello");
  }
}

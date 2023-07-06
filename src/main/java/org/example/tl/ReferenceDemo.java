package org.example.tl;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-06 18:51
 */
public class ReferenceDemo {

  public static void main(String[] args) {
    // strongReference();
    // softReference();
    // weakReference();

    phntomReference();
  }

  private static void phntomReference() {
    ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
    PhantomReference<MyObject> phantomReference =
        new PhantomReference<>(new MyObject(), referenceQueue);
    System.out.println(phantomReference.get());
    
  }

  private static void weakReference() {
    WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
    System.out.println("gc before:" + weakReference.get());
    System.gc();
    System.out.println("gc after:" + weakReference.get());
  }

  private static void softReference() {
    SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
    System.out.println("gc before:" + softReference);
    System.gc();
  }

  private static void strongReference() {
    MyObject myObject = new MyObject();
    System.out.println(myObject);
    myObject = null;
    System.gc();
    System.out.println(myObject);
  }
}

class MyObject {

  @Override
  protected void finalize() throws Throwable {
    System.out.println("invoke");
  }
}

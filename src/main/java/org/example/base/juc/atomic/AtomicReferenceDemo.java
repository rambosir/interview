package org.example.base.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @program interview
 * @author Ben
 * @date 2023-07-04 14:17
 */
public class AtomicReferenceDemo {

  public static void main(String[] args) {
    User z3 = new User("z3", 24);
    User l4 = new User("l4", 26);
    AtomicReference<User> atomicReference = new AtomicReference<>();
    atomicReference.set(z3);
    System.out.println(
        atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
    System.out.println(
            atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
  }
}

@Data
@AllArgsConstructor
class User {
  String userName;

  int age;
}

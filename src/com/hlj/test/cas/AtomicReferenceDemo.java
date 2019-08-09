package com.hlj.test.cas;

import java.util.concurrent.atomic.AtomicReference;

class User{
    String userName;
    int age;

    public User() {
    }

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}

/**
 * 类的原子引用
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {

        User z3 = new User("z3",22);
        User ls = new User("ls", 25);

        AtomicReference<User> userAtomicReference = new AtomicReference<>();

        userAtomicReference.set(z3);

        System.out.println(userAtomicReference.compareAndSet(z3,ls)+"\t"+userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(z3,ls)+"\t"+userAtomicReference.get().toString());
    }
}

package com.dawnmoon.big_event;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void SetAndGet(){

        // ThreadLocal 可以在不同的线程中存储和获取各自独立的值

        ThreadLocal tl = new ThreadLocal();

        new Thread(()->{
            tl.set("A");
            System.out.println(Thread.currentThread().getName()+"："+tl.get());
            System.out.println(Thread.currentThread().getName()+"："+tl.get());
            System.out.println(Thread.currentThread().getName()+"："+tl.get());
        }, "Thread1").start();

        new Thread(()->{
            tl.set("B");
            System.out.println(Thread.currentThread().getName()+"："+tl.get());
            System.out.println(Thread.currentThread().getName()+"："+tl.get());
            System.out.println(Thread.currentThread().getName()+"："+tl.get());
        }, "Thread2").start();
    }
}

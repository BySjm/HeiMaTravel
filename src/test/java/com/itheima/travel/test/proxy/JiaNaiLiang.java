package com.itheima.travel.test.proxy;

public class JiaNaiLiang implements TargetInterface{
    @Override
    public void sing(String name) {
        System.out.println("唱" + name);
    }

    @Override
    public void eat() {
        System.out.println("吃饭");
    }
}

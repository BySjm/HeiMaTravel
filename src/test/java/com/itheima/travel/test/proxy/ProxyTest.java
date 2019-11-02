package com.itheima.travel.test.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;

public class ProxyTest {

    @Test
    public void test() throws Exception {

        JiaNaiLiang jiaNaiLiang = new JiaNaiLiang();

        ClassLoader classLoader = jiaNaiLiang.getClass().getClassLoader();
        Class<?>[] interfaces = jiaNaiLiang.getClass().getInterfaces();
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("sing".equals(method.getName())){
                    System.out.println("先交费");
                }
                Object result = method.invoke(jiaNaiLiang, args);
                return result;
            }
        };
        TargetInterface xiaolu = (TargetInterface) Proxy.newProxyInstance(classLoader,interfaces,h);

        xiaolu.sing("爱是一道光");
    }

}

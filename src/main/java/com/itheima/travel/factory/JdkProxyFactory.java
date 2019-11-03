package com.itheima.travel.factory;

import java.io.*;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;

public class JdkProxyFactory {

    public static Object createLogProxy(Object target) {

        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();

        return Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[执行时间：]" + LocalDateTime.now());
            buffer.append("[执行类：]" + target.getClass().getName());
            buffer.append("[执行方法：]" + method.getName());
            Object result = null;
            try {
                result = method.invoke(target, args);
            } catch (Exception e) {
                e.printStackTrace();
                buffer.append("[异常信息：]" + e.getCause().getMessage());
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("D:/IDEA-workspace/travel/log.txt", true), "UTF-8"));
            bw.write(buffer.toString());
            bw.newLine();
            bw.close();
            return result;
        });
    }

}

package com.itheima.travel.factory;

import com.itheima.travel.service.UserService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class BeanFactory {

    public static Object getBean(String id) {
        Object object = null;
        try {
            // 1.通过类加载器读取配置文件，获取IO流
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
            // 2.创建dom4j核心解析器对象
            SAXReader saxReader = new SAXReader();
            // 3.dom4j读取io流，返回document对象
            Document document = saxReader.read(in);
            // 4.编写xpath表达式
            String xpath = "//bean[@id='" + id + "']";
            // 5.获取指定id的element元素对象
            Node node = document.selectSingleNode(xpath);
            Element element = (Element) node;
            // 6.获取class属性值：全限定类名
            String className = element.attributeValue("class");
            // 7.通过Class.forName() 加载到内存，创建class对象
            Class clazz = Class.forName(className);
            // 8.创建该对象的实例
            object = clazz.newInstance();
            object = JdkProxyFactory.createLogProxy(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 9.返回对象实例
        return object;
    }

    public static void main(String[] args) {
        UserService userService = (UserService)BeanFactory.getBean("UserService");
        System.out.println(userService);
    }

}

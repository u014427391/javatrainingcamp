package com.example.core.example.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *      开放java反射权限，允许调用类的private属性或者方法
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/09 19:10  修改内容:
 * </pre>
 */
public class AccessibleReflectionExample {

    public static void main(String[] args) throws Exception{

        test();
    }

    public static void test() throws Exception{
        // 创建class实例对象
        Class<?> cls = Class.forName("com.example.core.example.reflection.domain.User");
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        Object obj = con.newInstance();

        // 获取私有成员变量
        Field pwd = cls.getDeclaredField("password");
        // 开放私有变量的访问权限
        pwd.setAccessible(true);
        pwd.set(obj , "123456");

        // 私有方法调用
        Method method = cls.getDeclaredMethod("priToString");
        method.setAccessible(true);
        System.out.println(method.invoke(obj));
    }
}

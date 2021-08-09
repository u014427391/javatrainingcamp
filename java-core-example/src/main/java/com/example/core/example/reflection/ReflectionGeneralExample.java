package com.example.core.example.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *      反射通用例子
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/09 16:34  修改内容:
 * </pre>
 */
public class ReflectionGeneralExample {

    public static void main(String[] args) throws Exception{
        String allClassName = "com.example.core.example.reflection.domain.User";
        // 通过全类名获取class对象
        Class<?> cls = Class.forName(allClassName);
        System.out.println(cls);
        // 通过对象获取class
        System.out.println(cls.getClass());
        // 获取全类名
        System.out.println(cls.getName());
        // 获取包名
        System.out.println(cls.getClass().getPackage().getName());
        // 获取对象实例
        Object obj = cls.newInstance();
        System.out.println(obj);
        // 获取类属性
        Field field = cls.getField("username");
        field.set(obj , "admin");
        System.out.println(field.get(obj));
        // 获取所有类属性，private的成员变量没有权限访问
        Field[] fields = cls.getFields();
        for (Field field1 : fields) {
            System.out.println(field1.get(obj));
        }
        // 获取所有类属性包括private成员变量
        Field[] allFields = cls.getDeclaredFields();
        for (Field afield : allFields) {
            // 开放权限,私有的成员变量也能打印出来
            afield.setAccessible(true);
            System.out.println(afield.get(obj));
        }
        // 获取class方法，同样默认情况不能获取private的方法
        Method method = cls.getMethod("hello");
        System.out.println(method.invoke(obj));

    }


}

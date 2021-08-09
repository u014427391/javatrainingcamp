package com.example.core.example.reflection;

import com.example.core.example.reflection.domain.User;
import lombok.ToString;

import java.util.List;

/**
 * <pre>
 *       Java中哪些类型具有 Class类对象
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/09 20:08  修改内容:
 * </pre>
 */
public class GetClassObjectExample {

    public static void main(String[] args) {
        // 外部类
        Class<User> cls1 = User.class;
        // 接口
        Class<List> cls2 = List.class;
        // 数组
        Class<Integer[]> cls3 = Integer[].class;
        // 二维数组
        Class<String[][]> cls4 = String[][].class;
        // 注解
        Class<lombok.ToString> cls5 = ToString.class;

        // 枚举
        Class<Thread.State> cls6 = Thread.State.class;
        // 基本数据类型
        Class<Long> cls7 = Long.class;
        // void 数据类型
        Class<Void> cls8 = Void.class;
        // Class
        Class<Class> cls9 = Class.class;

        System.out.println(cls1);
        System.out.println(cls2);
        System.out.println(cls3);
        System.out.println(cls4);
        System.out.println(cls5);
        System.out.println(cls6);
        System.out.println(cls7);
        System.out.println(cls8);
        System.out.println(cls9);
    }
}

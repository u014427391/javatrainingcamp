package com.example.core.example.reflection;

import com.example.core.example.reflection.domain.User;

import java.util.Scanner;


public class TestProxyLoadExample {

    public static void main(String[] args) throws Exception{
        testProxyLoadExample();
    }

    /**
     *  测试动态加载和静态加载简单例子 <br>
     * @Date 2021/08/09 15:53
     * @return void
     */
    public static void testProxyLoadExample() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个数字：");
        Integer key =  scanner.nextInt();
        switch (key) {
            case 1 :
                // 静态加载：找不到类，直接编译不通过，项目没法跑
                User user = new User();
                break;
            case 2 :
                // 动态加载：调用时候，才会报错，类找不到不影响编译
                Class<?> cls = Class.forName("com.example.core.example.reflection.User");
                Object obj = cls.newInstance();
                break;
            default:
                break;
        }
    }
}

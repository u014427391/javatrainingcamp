package com.example.core.example.reflection;

import com.example.core.example.reflection.domain.User;

import java.lang.reflect.Field;

public class ReflectionSimpleExample {


    public static void main(String[] args) throws Exception{
        reflectionSimpleExample();

    }

    public static void reflectionSimpleExample() throws Exception{
        User user = new User();
        System.out.println(user.toString());

        Class<?> cls = Class.forName("com.example.core.example.reflection.domain.User");
        Object obj = cls.newInstance();
        System.out.println(obj);

        Field field = cls.getField("username");
        System.out.println(field.get(obj));

    }



}

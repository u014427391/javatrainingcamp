package com.example.concurrent.acomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *      AtomicReference example
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/12/04 18:18  修改内容:
 * </pre>
 */
public class AtomicReferenceExample {



    public static void main(String[] args) {
        User user1 = new User("tom", "***");
        User user2 = new User("jack", "***");
        User user3 = new User("admin", "***");
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(user1);

        System.out.println(atomicReference.compareAndSet(user1 , user2));
        System.out.println(atomicReference.get());

        System.out.println(atomicReference.compareAndSet(user1, user3));
        System.out.println(atomicReference.get());

    }

    static class User {
        private String username;
        private String password;
        User(String username , String password) {
            this.username = username;
            this.password = password;
        }
        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

}

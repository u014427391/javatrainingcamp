package com.example.datastructure.rbtree;

import java.util.Scanner;

/**
 * copy from com.bobo.util.treemap
 * @Date 2021/08/13 10:14
 */
public class RBTreeTest {
    public static void main(String[] args) {
        //新增节点
        //insertOpt();
        //删除节点
        deleteOpt();
    }

    /**
     * 插入操作
     */
    public static void insertOpt(){
        Scanner scanner=new Scanner(System.in);
        RBTree<String,Object> rbt=new RBTree<>();
        while (true){
            System.out.println("请输入你要插入的节点:");
            String key=scanner.next();
            System.out.println();
            //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
            if(key.length()==1){
                key="00"+key;
            }else if(key.length()==2){
                key="0"+key;
            }
            rbt.put(key , key);
            TreeOperation.show(rbt.getRoot());
        }
    }

    /**
     * 删除操作
     */
    public static void deleteOpt(){
        RBTree<String,Object> rbt=new RBTree<>();
        //测试1：预先造10个节点（1-10）
        String keyA=null;
        for (int i = 1; i <11 ; i++) {
            if((i+"").length()==1){
                keyA="00"+i;
            }else if((i+"").length()==2){
                keyA="0"+i;
            }
            rbt.put(keyA,keyA);
        }

        TreeOperation.show(rbt.getRoot());
        //以下开始删除
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("请输入你要删除的节点:");
            String key=scanner.next();
            System.out.println();
            //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
            if(key.length()==1){
                key="00"+key;
            }else if(key.length()==2){
                key="0"+key;
            }
            rbt.remove(key);
            TreeOperation.show(rbt.getRoot());
        }
    }
}

package com.example.core.example.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *      HashMap例子
 * </pre>
 * <p>
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/09/01 11:34  修改内容:
 * </pre>
 */
public class HashMapExample {

    public static void main(String[] args) {
        Map<String , Object> map = new HashMap<>(16);
        map.put("user", "userPwd");

        int hash = hash("user");
        int index  = (16 - 1) & hash;


    }

    static final int hash(Object key) {
        // 传入的key为null，返回默认值0
        if (key == null) return 0;
        // 计算哈希code
        int h = key.hashCode();
        // 将计算出来的hashCode右移16位，相当于乘于(1/2)的16次方
        int t = h >>> 16;
        // 将两个值做异或运算然后返回
        return h ^ t;
    }
}

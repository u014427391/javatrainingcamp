package com.example.core.example.annotation;

/**
 * <pre>
 *      jdk中的注释例子
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/10 16:58  修改内容:
 * </pre>
 */
@SuppressWarnings("all")
public class AnnotaionSimpleExample {

    /**
     * 接口过时，注释，不过还是可以使用的
     * @Param []
     * @return void
     */
    @Deprecated
    public void v1() {
    }

    /**
     * 新版接口，推荐用户使用
     * @Param []
     * @return void
     */
    public void v2(){}


    @Override
    public String toString() {
        return super.toString();
    }
}

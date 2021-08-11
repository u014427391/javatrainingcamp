package com.example.core.example.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *      自定义注解
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/10 17:17  修改内容:
 * </pre>
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyAnnotation {
    int value1() default 1;
    String value2() default "";
    String value3() default "str";
    int[] intarr() default {1,2,3,4,5,6};

}

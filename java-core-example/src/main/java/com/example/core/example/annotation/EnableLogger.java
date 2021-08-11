package com.example.core.example.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 *      日志记录注解类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/11 09:32  修改内容:
 * </pre>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableLogger {


}

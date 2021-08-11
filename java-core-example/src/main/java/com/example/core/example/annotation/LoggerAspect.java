package com.example.core.example.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *      日志打印Aspect类
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2021/08/11 09:42  修改内容:
 * </pre>
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class LoggerAspect {

    private Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("@annotation(com.example.core.example.annotation.EnableLogger)")
    public void pointCutMethod(){}

    @Around("pointCutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        if (LOG.isInfoEnabled()) {
            LOG.info(
                    "#method: {} ,args: {} , result：{}, used: {} ms",
                    MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName(),
                    joinPoint.getArgs(),
                    result,
                    System.currentTimeMillis() - start);
        }
        return result;
    }
}

package cn.sanleny.spring.aop.advice;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2018-12-31
 * @Description: cn.sanleny.spring.aop.advice
 * @Version: 1.0
 */
public interface MethodBeforeAdvice extends Advice {

    /**
     * 实现该方法,提供前置增强
     *
     * @param method 被增强的方法
     * @param args 方法的参数
     * @param target 被增强的目标对象
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}

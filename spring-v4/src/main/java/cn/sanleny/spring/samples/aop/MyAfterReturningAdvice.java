package cn.sanleny.spring.samples.aop;

import cn.sanleny.spring.aop.advice.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.samples.aop
 * @Version: 1.0
 */
public class MyAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(this + " 对 " + target + " 做了后置增强，得到的返回值=" + returnValue);
    }
}

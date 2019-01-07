package cn.sanleny.spring.samples.aop;

import cn.sanleny.spring.aop.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.samples.aop
 * @Version: 1.0
 */
public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(this + " 对 " + target + " 进行了前置增强！");
    }
}

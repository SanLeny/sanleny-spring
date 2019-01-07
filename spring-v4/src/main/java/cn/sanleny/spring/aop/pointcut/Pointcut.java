package cn.sanleny.spring.aop.pointcut;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.pointcut
 * @Version: 1.0
 */
public interface Pointcut {

        boolean matchsClass(Class<?> targetClass);

        boolean matchsMethod(Method method,Class<?> targetClass);
}

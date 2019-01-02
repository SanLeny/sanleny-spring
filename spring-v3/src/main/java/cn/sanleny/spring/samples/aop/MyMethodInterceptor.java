package cn.sanleny.spring.samples.aop;

import cn.sanleny.spring.aop.advice.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.samples.aop
 * @Version: 1.0
 */
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(this + "对 " + target + "进行了环绕 -- 前增强");
        Object ret = method.invoke(target, args);
        System.out.println(this + "对 " + target + "进行了环绕 -- 后增强。方法的返回值为：" + ret);
        return ret;
    }
}

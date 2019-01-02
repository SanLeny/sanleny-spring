package cn.sanleny.spring.aop;

import cn.sanleny.spring.aop.advice.AfterReturningAdvice;
import cn.sanleny.spring.aop.advice.MethodBeforeAdvice;
import cn.sanleny.spring.aop.advice.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop
 * @Version: 1.0
 */
public class AopAdviceChainInvocation {

    private static Method invokeMethod;
    static {
        try {
            invokeMethod = AopAdviceChainInvocation.class.getMethod("invoke", null);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private Object proxy;
    private Object target;
    private Method method;
    private Object[] args;
    private List<Object> advices;

    public AopAdviceChainInvocation(Object proxy, Object target, Method method, Object[] args, List<Object> advices) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.args = args;
        this.advices = advices;
    }

    // 责任链执行记录索引号
    private int i = 0;

    public Object invoke() throws Throwable {
        if (this.advices.size() > i) {
            Object advice = this.advices.get(i++);
            if (advice instanceof MethodBeforeAdvice) {
                // 前置增强
                ((MethodBeforeAdvice) advice).before(method, args, target);
            } else if (advice instanceof MethodInterceptor) {
                // 环绕增强和异常处理增强。注意这里给入的method 和 对象 是invoke方法和链对象
                return ((MethodInterceptor) advice).invoke(invokeMethod, null, this);
            } else if (advice instanceof AfterReturningAdvice) {
                // 后置增强，先得得到结果，再执行后置增强逻辑
                Object returnValue = this.invoke();
                ((AfterReturningAdvice) advice).afterReturning(returnValue, method, args, target);
                return returnValue;
            }
            return this.invoke();
        } else {
            return method.invoke(target, args);
        }
    }
}

package cn.sanleny.spring.aop;

import cn.sanleny.spring.aop.advisor.Advisor;
import cn.sanleny.spring.beans.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop
 * @since v3
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private String beanName;
    private Object target;
    private List<Advisor> matchAdvisors;

    private BeanFactory beanFactory;

    public JdkDynamicAopProxy(String beanName,Object target,List<Advisor> matchAdvisors,BeanFactory beanFactory) {
        this.beanName = beanName;
        this.target = target;
        this.matchAdvisors = matchAdvisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getProxy() {
        return this.getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        System.out.println(">>>为类"+target+" 创建JDK动态代理");
        return Proxy.newProxyInstance(classLoader,target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, beanFactory);
    }
}

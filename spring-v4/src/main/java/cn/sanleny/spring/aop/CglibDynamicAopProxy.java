package cn.sanleny.spring.aop;


import cn.sanleny.spring.aop.advisor.Advisor;
import cn.sanleny.spring.beans.BeanDefinition;
import cn.sanleny.spring.beans.BeanFactory;
import cn.sanleny.spring.beans.DefaultBeanFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop
 * @since v3
 */
public class CglibDynamicAopProxy implements AopProxy, MethodInterceptor {

    private static Enhancer enhancer=new Enhancer();

    private Object target;
    private String beanName;
    private List<Advisor> matchAdvisors;

    private BeanFactory beanFactory;

    public CglibDynamicAopProxy(String beanName,Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) {
        this.target = target;
        this.beanName = beanName;
        this.matchAdvisors = matchAdvisors;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getProxy() {
        return this.getProxy(target.getClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        System.out.println(">>>为类"+target+" 创建CGLIB代理");
        Class<?> superClass = this.target.getClass();
        enhancer.setSuperclass(superClass);
        enhancer.setInterfaces(this.getClass().getInterfaces());
        enhancer.setCallback(this);
        Constructor<?> constructor = null;
        try {
            constructor=superClass.getConstructor(new Class[]{});
        } catch (NoSuchMethodException e) {}
        if(constructor !=null){
            //如果存在构造方法
            return enhancer.create();
        }else{
            BeanDefinition beanDefinition = ((DefaultBeanFactory) beanFactory).getBeanDefinition(beanName);
            return enhancer.create(beanDefinition.getConstgructor().getParameterTypes(),
                    beanDefinition.getConstructorArgumentRealValues());
        }
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, beanFactory);
    }
}

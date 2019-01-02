package cn.sanleny.spring.aop;

import cn.sanleny.spring.aop.advisor.Advisor;
import cn.sanleny.spring.beans.BeanFactory;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop
 * @Version: 1.0
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Throwable {
        Class<?> targetClass = bean.getClass();
        if (targetClass == null) {
            throw new Throwable("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
        }
        if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return new JdkDynamicAopProxy(beanName, bean, matchAdvisors, beanFactory);
        }
        return new CglibDynamicAopProxy(beanName, bean, matchAdvisors, beanFactory);
    }

}

package cn.sanleny.spring.aop;

import cn.sanleny.spring.aop.advisor.Advisor;
import cn.sanleny.spring.beans.BeanFactory;

import java.util.List;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop
 * @since v3
 */
public interface AopProxyFactory {

    AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Throwable;

    /**
     * 获得默认的AopProxyFactory实例
     *
     * @return AopProxyFactory
     */
    static AopProxyFactory getDefaultAopProxyFactory() {
        return new DefaultAopProxyFactory();
    }

}

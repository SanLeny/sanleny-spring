package cn.sanleny.spring.beans;

import cn.sanleny.spring.aop.advisor.Advisor;
import cn.sanleny.spring.aop.advisor.AdvisorRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * AOP 增强处理的观察者实现
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.beans
 * @since v3
 */
public class AdvisorAutoProxyCreator implements AdvisorRegistry,BeanPostProcessor {

    private List<Advisor> advisors;

    public AdvisorAutoProxyCreator() {
        this.advisors=new ArrayList<>();
    }

    @Override
    public void registryAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    public List<Advisor> getAdvisors() {
        return this.advisors;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Throwable {

        return bean;
    }
}

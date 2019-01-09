package cn.sanleny.spring.context;

import cn.sanleny.spring.beans.BeanFactory;
import cn.sanleny.spring.beans.BeanPostProcessor;
import cn.sanleny.spring.beans.PreBuildBeanFactory;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected BeanFactory beanFactory;

    public AbstractApplicationContext() {
        this.beanFactory = new PreBuildBeanFactory();
    }

    @Override
    public Object getBean(String name) throws Throwable {
        return beanFactory.getBean(name);
    }

    @Override
    public void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanFactory.registerBeanPostProcessor(beanPostProcessor);
    }
}

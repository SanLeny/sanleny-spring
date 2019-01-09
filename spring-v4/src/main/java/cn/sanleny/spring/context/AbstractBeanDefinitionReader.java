package cn.sanleny.spring.context;

import cn.sanleny.spring.beans.BeanDefinitionRegistry;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    protected BeanDefinitionRegistry registry;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }
}

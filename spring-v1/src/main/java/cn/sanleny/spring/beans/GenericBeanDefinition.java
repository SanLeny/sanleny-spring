package cn.sanleny.spring.beans;

/**
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public class GenericBeanDefinition implements BeanDefinition {


    @Override
    public Class<?> getBeanClass() {
        return null;
    }

    @Override
    public String getScope() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public String getFactoryBeanName() {
        return null;
    }

    @Override
    public String getFactoryMethoodName() {
        return null;
    }

    @Override
    public String getInitMethodName() {
        return null;
    }

    @Override
    public String getDestroyMethodName() {
        return null;
    }
}

package cn.sanleny.spring.context;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public interface BeanDefinitionReader {

    void loadBeanDefintions(Resource resource) throws Throwable;

    void loadBeanDefintions(Resource... resource) throws Throwable;
}

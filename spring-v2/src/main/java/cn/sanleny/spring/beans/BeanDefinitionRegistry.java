package cn.sanleny.spring.beans;

/**
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册bean
     * @param beanName
     * @param beanDefinition
     * @throws BeanDefinitionRegistException
     */
    void registryBeanDefinition(String beanName,BeanDefinition beanDefinition) throws BeanDefinitionRegistException;

    /**
     * 获取 bean
     * @Author LG
     * @Date 2018-12-24
     * @Version 1.0
     * @param beanName
     * @return: cn.sanleny.spring.beans.BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 判断bean是否存在
     * @Author LG
     * @Date 2018-12-24
     * @Version 1.0
     * @param beanName
     * @return: boolean
     */
    boolean contionsBeanDefinition(String beanName);
}

package cn.sanleny.spring.beans;

/**
 * 扩展点
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.beans
 * @since v3
 */
public interface BeanPostProcessor {

    /**
     * 初始化前
     * @param bean
     * @param beanName
     * @return
     * @throws Throwable
     */
    default Object postProcessBeforeInitialization(Object bean,String beanName) throws Throwable{
        return bean;
    }

    /**
     * 初始化后
     * @param bean
     * @param beanName
     * @return
     * @throws Throwable
     */
    default Object postProcessAfterInitialization(Object bean,String beanName) throws Throwable{
        return bean;
    }

}

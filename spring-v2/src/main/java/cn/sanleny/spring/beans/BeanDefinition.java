package cn.sanleny.spring.beans;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * bean 定义接口
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.beans
 * @Version: 1.0
 */
public interface BeanDefinition {

    String SCOPE_SINGLETION="singleton";
    String SCOPE_PROTOTYPE="prototype";

    /**
     * 类
     */
    Class<?> getBeanClass();
    /**
     * Scope
     */
    String getScope();
    /**
     * 是否单例
     */
    boolean isSingleton();
    /**
     * 是否原型
     */
    boolean isPrototype();
    /**
     * 工厂bean名
     */
    String getFactoryBeanName();
    /**
     * 工厂方法名
     */
    String getFactoryMethodName();
    /**
     * 初始化方法
     */
    String getInitMethodName();
    /**
     * 销毁方法
     */
    String getDestroyMethodName();

    /**
     * 校验bean定义的合法性
     */
    default boolean validate(){
        // 没定义class,工厂bean或工厂方法没指定，则不合法。
        if(this.getBeanClass()==null){
            if(StringUtils.isBlank(getFactoryBeanName()) || StringUtils.isBlank(getFactoryMethodName())){
                return  false;
            }
        }

        // 定义了类，又定义工厂bean，不合法
        if(this.getBeanClass()!=null && StringUtils.isNotBlank(getFactoryBeanName())){
            return false;
        }
        return true;
    }

    /**
     * <p>获得构造参数定义</p>
     * @since v2
     * @return
     */
    List<?>  getConstructorArgumentValues();

    /* 下面的四个方法是供beanFactory中使用的 */

    /**
     * @since v2
     * @return
     */
    Constructor<?> getConstgructor();

    /**
     * @since v2
     * @param constructor
     */
    void setConstructor(Constructor<?> constructor);

    /**
     * @since v2
     * @return
     */
    Method getFactoryMethod();

    /**
     * @since v2
     * @param factoryMethod
     */
    void setFactoryMethod(Method factoryMethod);

    /**
     * @since v2
     */
    List<PropertyValue> getPropertyValues();

}

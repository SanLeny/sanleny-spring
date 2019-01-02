package cn.sanleny.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展 DefaultBeanFactory
 * 对于单例bean，提前实例化
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public class PreBuildBeanFactory extends DefaultBeanFactory{

    private List<String> beanNames = new ArrayList<>();

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistException {
        super.registryBeanDefinition(beanName, beanDefinition);
        synchronized (beanName){
            beanNames.add(beanName);
        }
    }

    public void preInstantiateSingletons() throws Throwable {
        synchronized (beanNames){
            for(String beanName:beanNames){
                BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
                if(beanDefinition.isSingleton()){
                    this.doGetBean(beanName);
//                    System.out.println("preInstantiate: name=" + beanName + " " + beanDefinition);
                }
            }
        }
    }
}

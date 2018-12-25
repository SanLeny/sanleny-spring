package cn.sanleny.spring.test;

import cn.sanleny.spring.beans.*;
import cn.sanleny.spring.samples.ABean;
import cn.sanleny.spring.samples.ABeanFactory;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * 手写spring-v1  IOC 测试
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.test
 * @Version: 1.0
 */
public class DefaultBeanFactoryTest {

    static DefaultBeanFactory defaultBeanFactory=new DefaultBeanFactory();
//    static PreBuildBeanFactory defaultBeanFactory=new PreBuildBeanFactory();

    @Test
    public void testRegistry() throws BeanDefinitionRegistException {
        GenericBeanDefinition gb=new GenericBeanDefinition();

        gb.setBeanClass(ABean.class);
//        gb.setScope(BeanDefinition.SCOPE_SINGLETION);
         gb.setScope(BeanDefinition.SCOPE_PROTOTYPE);

        gb.setInitMethodName("init");
        gb.setDestroyMethodName("destroy");

        defaultBeanFactory.registryBeanDefinition("aBean",gb);
    }

    @Test
    public void testRegistStaticFactoryMethod() throws BeanDefinitionRegistException {
        GenericBeanDefinition gb=new GenericBeanDefinition();
        gb.setBeanClass(ABeanFactory.class);
        gb.setFactoryMethodName("getABean");
        defaultBeanFactory.registryBeanDefinition("staticABean",gb);
    }

    @Test
    public void testRegistFactoryMethod() throws BeanDefinitionRegistException {
        GenericBeanDefinition gb=new GenericBeanDefinition();
        gb.setBeanClass(ABeanFactory.class);
        String fname="factory";
        defaultBeanFactory.registryBeanDefinition(fname,gb);//注册beanFactory

        gb = new GenericBeanDefinition();
        gb.setFactoryBeanName(fname);
        gb.setFactoryMethodName("getABean2");
        gb.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        defaultBeanFactory.registryBeanDefinition("factoryABean",gb);

    }

    @AfterClass
    public static void testGetBean() throws Exception {
        System.out.println("构造方法方式----------------");
        for (int i = 0; i < 3; i++) {
            ABean ab = (ABean) defaultBeanFactory.getBean("aBean");
            ab.doSomthing();
        }

        System.out.println("静态工厂方法方式----------------");
        for (int i = 0; i < 3; i++) {
            ABean ab = (ABean) defaultBeanFactory.getBean("staticABean");
            ab.doSomthing();
        }

        System.out.println("工厂方法方式----------------");
        for (int i = 0; i < 3; i++) {
            ABean ab = (ABean) defaultBeanFactory.getBean("factoryABean");
            ab.doSomthing();
        }
        defaultBeanFactory.close();
    }


}

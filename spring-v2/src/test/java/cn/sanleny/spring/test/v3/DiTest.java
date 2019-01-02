package cn.sanleny.spring.test.v3;

import cn.sanleny.spring.beans.BeanReference;
import cn.sanleny.spring.beans.GenericBeanDefinition;
import cn.sanleny.spring.beans.PreBuildBeanFactory;
import cn.sanleny.spring.samples.ABean;
import cn.sanleny.spring.samples.ABeanFactory;
import cn.sanleny.spring.samples.CBean;
import cn.sanleny.spring.samples.CCBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * spring-v2 依赖注入 DI测试
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.test.v2
 * @Version: 1.0
 */
public class DiTest {

    static PreBuildBeanFactory bf = new PreBuildBeanFactory();

    //构造方法 DI测试
    @Test
    public void testConstructorDI() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABean.class);
        List<Object> args = new ArrayList<>();
        args.add("abean01");
        args.add(new BeanReference("cbean"));
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("abean", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(CBean.class);
        args = new ArrayList<>();
        args.add("cbean01");
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("cbean",bd);

        bf.preInstantiateSingletons();//实例化

        ABean aBean = (ABean) bf.getBean("abean");
        aBean.doSomthing();

        CBean cBean = (CBean) bf.getBean("cbean");
        System.out.println(cBean.getName());

    }

    //静态工厂方法 DI 测试
    @Test
    public void testStaticFactoryMethodDI() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABeanFactory.class);
        bd.setFactoryMethodName("getABean");
        List<Object> args = new ArrayList<>();
        args.add("abean02");
        args.add(new BeanReference("cbean02"));
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("abean02", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(CBean.class);
        args = new ArrayList<>();
        args.add("cbean02");
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("cbean02", bd);

        bf.preInstantiateSingletons();//实例化

        ABean aBean = (ABean) bf.getBean("abean02");
        aBean.doSomthing();

        CBean cBean = (CBean) bf.getBean("cbean02");
        System.out.println(cBean.getName());
    }

    //工厂方法 DI 测试
    @Test
    public void testFactoryMethodDI() throws Exception {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setFactoryBeanName("abeanFactory");
        bd.setFactoryMethodName("getABean2");
        List<Object> args = new ArrayList<>();
        args.add("abean03");
        args.add(new BeanReference("cbean03"));
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("abean03", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(CBean.class);
        args = new ArrayList<>();
        args.add("cbean03");
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("cbean03", bd);


        bd = new GenericBeanDefinition();
        bd.setBeanClass(ABeanFactory.class);
        bf.registryBeanDefinition("abeanFactory", bd);

        bf.preInstantiateSingletons();//实例化

        ABean aBean = (ABean) bf.getBean("abean03");
        aBean.doSomthing();
        CBean cBean = (CBean) bf.getBean("cbean03");
        System.out.println(cBean.getName());

    }


    @Test
    public void testChildTypeDI() throws Exception {

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(ABean.class);
        List<Object> args = new ArrayList<>();
        args.add("abean04");
        args.add(new BeanReference("ccbean01"));
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("abean04", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(CCBean.class);
        args = new ArrayList<>();
        args.add("ccbean01");
        bd.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("ccbean01", bd);

        bf.preInstantiateSingletons();//实例化

        ABean aBean = (ABean) bf.getBean("abean04");
        aBean.doSomthing();

        CBean cBean = (CBean) bf.getBean("ccbean01");
        System.out.println(cBean.getName());

    }

}

package cn.sanleny.spring.test.v3;

import cn.sanleny.spring.beans.BeanReference;
import cn.sanleny.spring.beans.GenericBeanDefinition;
import cn.sanleny.spring.beans.PreBuildBeanFactory;
import cn.sanleny.spring.beans.PropertyValue;
import cn.sanleny.spring.samples.ABean;
import cn.sanleny.spring.samples.CBean;
import cn.sanleny.spring.samples.FBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * spring-v2 DI 属性依赖 测试
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.test.v2
 * @Version: 1.0
 */
public class PropertyDItest {

    static PreBuildBeanFactory bf = new PreBuildBeanFactory();


    @Test
    public void testPropertyDI() throws Exception{

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
        bf.registryBeanDefinition("cbean", bd);

        bd = new GenericBeanDefinition();
        bd.setBeanClass(FBean.class);
        List<PropertyValue> propertyValues = new ArrayList<>();
        propertyValues.add(new PropertyValue("name", "FFBean01"));
        propertyValues.add(new PropertyValue("age", 18));
        propertyValues.add(new PropertyValue("aBean", new BeanReference("abean")));
        bd.setPropertyValues(propertyValues);
        bf.registryBeanDefinition("fbean", bd);

        bf.preInstantiateSingletons();//实例化

        FBean fbean = (FBean) bf.getBean("fbean");
        System.out.println(fbean.getName() + " " + fbean.getAge());
        fbean.getaBean().doSomthing();


    }
}

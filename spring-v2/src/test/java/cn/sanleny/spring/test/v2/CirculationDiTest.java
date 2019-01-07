package cn.sanleny.spring.test.v2;

import cn.sanleny.spring.beans.BeanReference;
import cn.sanleny.spring.beans.GenericBeanDefinition;
import cn.sanleny.spring.beans.PreBuildBeanFactory;
import cn.sanleny.spring.samples.DBean;
import cn.sanleny.spring.samples.EBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * spring-v2 DI 循环依赖 测试
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.test.v2
 * @Version: 1.0
 */
public class CirculationDiTest {

    static PreBuildBeanFactory bf = new PreBuildBeanFactory();

    @Test
    public void testCirculationDI() throws Exception {

        GenericBeanDefinition gb=new GenericBeanDefinition();
        gb.setBeanClass(DBean.class);
        List<Object> args = new ArrayList<>();
        args.add(new BeanReference("ebean"));
        gb.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("dbean",gb);

        gb=new GenericBeanDefinition();
        gb.setBeanClass(EBean.class);
        args = new ArrayList<>();
        args.add(new BeanReference("dbean"));
        gb.setConstructorArgumentValues(args);
        bf.registryBeanDefinition("ebean",gb);

        bf.preInstantiateSingletons();//实例化

    }


}

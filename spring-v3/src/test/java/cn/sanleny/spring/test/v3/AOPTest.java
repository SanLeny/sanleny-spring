package cn.sanleny.spring.test.v3;

import cn.sanleny.spring.aop.AdvisorAutoProxyCreator;
import cn.sanleny.spring.aop.advisor.AspectJPointcutAdvisor;
import cn.sanleny.spring.beans.BeanReference;
import cn.sanleny.spring.beans.GenericBeanDefinition;
import cn.sanleny.spring.beans.PreBuildBeanFactory;
import cn.sanleny.spring.samples.ABean;
import cn.sanleny.spring.samples.CBean;
import cn.sanleny.spring.samples.aop.MyAfterReturningAdvice;
import cn.sanleny.spring.samples.aop.MyBeforeAdvice;
import cn.sanleny.spring.samples.aop.MyMethodInterceptor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AOPTest {

	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void test() throws Throwable {

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

		// 前置增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyBeforeAdvice.class);
		bf.registryBeanDefinition("myBeforeAdvice", bd);

		// 环绕增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyMethodInterceptor.class);
		bf.registryBeanDefinition("myMethodInterceptor", bd);

		// 后置增强advice bean注册
		bd = new GenericBeanDefinition();
		bd.setBeanClass(MyAfterReturningAdvice.class);
		bf.registryBeanDefinition("myAfterReturningAdvice", bd);

		// 往BeanFactory中注册AOP的BeanPostProcessor
		AdvisorAutoProxyCreator aapc = new AdvisorAutoProxyCreator();
		bf.registerBeanPostProcessor(aapc);
		// 向AdvisorAutoProxyCreator注册Advisor
		aapc.registryAdvisor(
				new AspectJPointcutAdvisor("myBeforeAdvice", "execution(* cn.sanleny.spring.samples.ABean.*(..))"));
		// 向AdvisorAutoProxyCreator注册Advisor
		aapc.registryAdvisor(
				new AspectJPointcutAdvisor("myMethodInterceptor", "execution(* cn.sanleny.spring.samples.ABean.do*(..))"));
		// 向AdvisorAutoProxyCreator注册Advisor
		aapc.registryAdvisor(new AspectJPointcutAdvisor("myAfterReturningAdvice",
				"execution(* cn.sanleny.spring.samples.ABean.do*(..))"));

		bf.preInstantiateSingletons();

		ABean abean = (ABean) bf.getBean("abean");

		abean.doSomthing();
		System.out.println("--------------------------------");
		abean.sayHello();
	}
}

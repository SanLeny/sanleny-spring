package cn.sanleny.spring.context;

import cn.sanleny.spring.beans.BeanDefinitionRegistry;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context.config.annotation
 * @Version: 1.0
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    private ClassPathBeanDefinitionScanner scanner;

    public AnnotationApplicationContext(String ... basePackages) throws Throwable {
        scanner=new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) this.beanFactory);
        scanner.scan(basePackages);
    }

    @Override
    public Resource getResource(String location) {
        return null;
    }
}

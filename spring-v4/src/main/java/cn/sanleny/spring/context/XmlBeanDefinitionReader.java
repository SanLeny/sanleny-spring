package cn.sanleny.spring.context;

import cn.sanleny.spring.beans.BeanDefinitionRegistry;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void loadBeanDefintions(Resource resource) throws Throwable {
        this.loadBeanDefintions(new Resource[]{resource});
    }

    @Override
    public void loadBeanDefintions(Resource... resources) throws Throwable {
        if(resources != null && resources.length > 0){
            for (Resource resource : resources) {
                //TODO registry beandefinition
                this.parseXml(resource);
            }
        }

    }

    private void parseXml(Resource resource) {

    }
}

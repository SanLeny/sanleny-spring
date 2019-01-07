package cn.sanleny.spring.beans;

/**
 * 用于依赖注入中描述bean依赖
 *
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public class BeanReference {

    private String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}

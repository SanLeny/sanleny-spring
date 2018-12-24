package cn.sanleny.spring.beans;

/**
 * bean 工厂
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public interface BeanFactory {

    /**
     * 获取 bean
     * @param name  bean名称
     * @return bean 实例
     * @throws Exception
     */
    Object getBean(String name) throws Exception;
}

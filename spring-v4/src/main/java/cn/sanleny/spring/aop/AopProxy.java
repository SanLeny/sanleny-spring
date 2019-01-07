package cn.sanleny.spring.aop;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop
 * @since v3
 */
public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);

}

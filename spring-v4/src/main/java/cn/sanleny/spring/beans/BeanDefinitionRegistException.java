package cn.sanleny.spring.beans;

/**
 * 自定义一个异常
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public class BeanDefinitionRegistException extends  Exception {

    public BeanDefinitionRegistException(String message){
        super(message);
    }

    public BeanDefinitionRegistException(String message,Throwable throwable){
        super(message,throwable);
    }
}

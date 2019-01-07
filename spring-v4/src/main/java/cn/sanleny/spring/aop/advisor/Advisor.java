package cn.sanleny.spring.aop.advisor;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.advisor
 * @Version: 1.0
 */
public interface Advisor {

    String getAdviceBeanName();

    String getExpression();
}

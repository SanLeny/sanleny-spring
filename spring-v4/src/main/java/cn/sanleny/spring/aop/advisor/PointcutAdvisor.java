package cn.sanleny.spring.aop.advisor;

import cn.sanleny.spring.aop.pointcut.Pointcut;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.advisor
 * @Version: 1.0
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}

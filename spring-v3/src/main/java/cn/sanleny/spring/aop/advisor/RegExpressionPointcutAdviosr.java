package cn.sanleny.spring.aop.advisor;

import cn.sanleny.spring.aop.pointcut.Pointcut;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.advisor
 * @Version: 1.0
 */
public class RegExpressionPointcutAdviosr extends AbstractPointcutAdvisor {

    public RegExpressionPointcutAdviosr(String adviceBeanName, String expression) {
        super(adviceBeanName, expression);
    }

    @Override
    public Pointcut getPointcut() {
        //TODO 这里返回相关的 pointcut
        return null;
    }
}

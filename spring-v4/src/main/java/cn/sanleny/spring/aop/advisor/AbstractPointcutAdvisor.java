package cn.sanleny.spring.aop.advisor;

import cn.sanleny.spring.aop.pointcut.Pointcut;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.advisor
 * @Version: 1.0
 */
public abstract class AbstractPointcutAdvisor implements PointcutAdvisor{

    private String adviceBeanName;

    private String expression;

    public AbstractPointcutAdvisor(String adviceBeanName, String expression) {
        this.adviceBeanName = adviceBeanName;
        this.expression = expression;
    }

    @Override
    public abstract Pointcut getPointcut();

    @Override
    public String getAdviceBeanName() {
        return this.adviceBeanName;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }
}

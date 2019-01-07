package cn.sanleny.spring.aop.advisor;

import cn.sanleny.spring.aop.pointcut.AspectJExpressionPointcut;
import cn.sanleny.spring.aop.pointcut.Pointcut;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.advisor
 * @Version: 1.0
 */
public class AspectJPointcutAdvisor implements PointcutAdvisor {

    private String adviceBeanName;

    private String expression;

    private AspectJExpressionPointcut pointcut;

    public AspectJPointcutAdvisor(String adviceBeanName, String expression) {
        this.adviceBeanName = adviceBeanName;
        this.expression = expression;
        pointcut =new AspectJExpressionPointcut(expression);
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public String getAdviceBeanName() {
        return this.adviceBeanName;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }
}

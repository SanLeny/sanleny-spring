package cn.sanleny.spring.aop.pointcut;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.aop.pointcut
 * @Version: 1.0
 */
public class AspectJExpressionPointcut implements Pointcut {

    private static PointcutParser pp=PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    private String expression;

    private PointcutExpression pe;

    public AspectJExpressionPointcut(String expression) {
        this.expression = expression;
        pe = pp.parsePointcutExpression(expression);
    }

    @Override
    public boolean matchsClass(Class<?> targetClass) {
        return pe.couldMatchJoinPointsInType(targetClass);
    }

    @Override
    public boolean matchsMethod(Method method, Class<?> targetClass) {
        ShadowMatch match = pe.matchesMethodExecution(method);
        return match.alwaysMatches();
    }

    public String getExpression() {
        return expression;
    }
}

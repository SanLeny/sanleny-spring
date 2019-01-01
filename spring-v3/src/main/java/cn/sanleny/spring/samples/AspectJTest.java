package cn.sanleny.spring.samples;

import cn.sanleny.spring.beans.DefaultBeanFactory;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;
import org.aspectj.weaver.tools.TypePatternMatcher;

import java.lang.reflect.Method;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public class AspectJTest {

    public static void main(String args[]) throws NoSuchMethodException {
        PointcutParser pp = PointcutParser
                .getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
        TypePatternMatcher tpm = pp.parseTypePattern("cn.sanleny.spring..*");
        PointcutExpression pe = pp.parsePointcutExpression("execution(* cn.sanleny.spring.samples.Driver.start*(..))");
        Class<?> cl = CCBean.class;
        Method aMethod = cl.getMethod("getName", null);
        ShadowMatch sm = pe.matchesMethodExecution(aMethod);
        System.out.println("是否匹配方法："+sm.alwaysMatches());

        System.out.println("是否匹配类CCBean.class :"+pe.couldMatchJoinPointsInType(cl));
        System.out.println("是否匹配类DefaultBeanFactory.class :"+pe.couldMatchJoinPointsInType(DefaultBeanFactory.class));

        for (Method m : cl.getMethods()) {
            System.out.println(m.getName());
        }
    }
}

package cn.sanleny.spring.samples;

import java.lang.reflect.Constructor;

/**
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public class Test {

    public Test(String a, int b, char... c) {
        System.out.println(a + b + c);
    }

    public static void main(String args[]) throws Exception {
        Class<?> c = Test.class;
        Constructor ct = c.getConstructor(new Class<?>[] { String.class, int.class, char[].class });

        System.out.println(ct);

        Object instance = ct.newInstance(new Object[] { "aaaa", 4, new char[] { 'c' } });

        System.out.println(instance);
    }
}

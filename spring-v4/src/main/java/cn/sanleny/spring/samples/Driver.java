package cn.sanleny.spring.samples;

/**
 * @Author: sanleny
 * @Date: 2019-01-01
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public interface Driver {

    void start();

    default void stop(){
        System.out.println("默认实现stop》》》");
    }
}

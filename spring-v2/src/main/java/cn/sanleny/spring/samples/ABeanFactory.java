package cn.sanleny.spring.samples;

/**
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public class ABeanFactory {

    public static ABean getABean(){
        return new ABean();
    }

    public ABean getABean2(){
        return new ABean();
    }
}

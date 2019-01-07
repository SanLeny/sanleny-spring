package cn.sanleny.spring.samples;

/**
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public class CCBean extends CBean implements Driver{

    public CCBean(String name) {
        super(name);
    }

    @Override
    public void start() {
        System.out.println("CCBean.class start...");
    }
}

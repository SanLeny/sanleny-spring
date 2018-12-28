package cn.sanleny.spring.samples;

/**
 * @Author: LG
 * @Date: 2018-12-29
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public class FBean {

    private String name;

    private int age;

    private ABean aBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ABean getaBean() {
        return aBean;
    }

    public void setaBean(ABean aBean) {
        this.aBean = aBean;
    }
}

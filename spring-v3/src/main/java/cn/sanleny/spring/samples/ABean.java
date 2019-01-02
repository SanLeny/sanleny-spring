package cn.sanleny.spring.samples;

/**
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
public class ABean {

    private String name;
    private CBean cb;

    public ABean(String name, CBean cBean) {
        this.name = name;
        this.cb = cBean;
        System.out.println("调用了含有name、CBean参数的构造方法");
    }

    public ABean(String name, CCBean cb) {
        this.name = name;
        this.cb = cb;
        System.out.println("调用了含有name、CCBean参数的构造方法");
    }

    public ABean(CBean cb) {
        super();
        this.cb = cb;
        System.out.println("调用了只含有CBean参数的构造方法");
    }

    public void doSomthing() {
        System.out.println(System.currentTimeMillis() + " " + this.name  + " cb.name=" + this.cb.getName());
    }

    public void init() {
        System.out.println("ABean.init() 执行了");
    }

    public void destroy() {
        System.out.println("ABean.destroy() 执行了");
    }

    public void sayHello() {
        System.out.println("Hello, ABean ！");
    }
}

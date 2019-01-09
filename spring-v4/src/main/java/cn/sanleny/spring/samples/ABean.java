package cn.sanleny.spring.samples;

import cn.sanleny.spring.context.config.annotation.Autowired;
import cn.sanleny.spring.context.config.annotation.Component;
import cn.sanleny.spring.context.config.annotation.Qualifier;
import cn.sanleny.spring.context.config.annotation.Value;

/**
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.samples
 * @Version: 1.0
 */
@Component(initMethodName = "init",destroyMethodName = "destroy")
public class ABean {

    private String name;
    private CBean cb;

    @Autowired
    private DBean dBean;

    @Autowired
    public ABean(@Value("sanleny") String name,@Qualifier("cbean01") CBean cBean) {
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

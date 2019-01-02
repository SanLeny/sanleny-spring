package cn.sanleny.spring.beans;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: LG
 * @Date: 2018-12-24
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public class DefaultBeanFactory implements BeanFactory,BeanDefinitionRegistry, Closeable {

    private Map<String,BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>(256);

    private Map<String,Object> beanMap=new ConcurrentHashMap<>(256);

    private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();

    private List<BeanPostProcessor> beanPostProcessors=Collections.synchronizedList(new ArrayList<>());

    @Override
    public void registerBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionRegistException {
        Objects.requireNonNull(beanName,"注册bean需要给入beanName");
        Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");

        //校验给如的bean 是否合法
        if(!beanDefinition.validate()){
            throw new BeanDefinitionRegistException("名字为[" + beanName + "] 的bean定义不合法：" + beanDefinition);
        }

        if(this.contionsBeanDefinition(beanName)){
            throw new BeanDefinitionRegistException("名字为[" + beanName + "] 的bean定义已存在：" + this.getBeanDefinition(beanName));
        }

        this.beanDefinitionMap.put(beanName,beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean contionsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public Object getBean(String beanName) throws Throwable {
        return this.doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) throws Throwable {
        Objects.requireNonNull(beanName,"beanName不能为空");

        Object instance=beanMap.get(beanName);
        if(null != instance){
            return instance;
        }
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        Objects.requireNonNull(beanDefinition,"不存在name为：" + beanName + " beean 定义！");

        // 记录正在创建的Bean
        Set<String> ingBeans = this.buildingBeans.get();
        if (ingBeans == null) {
            ingBeans = new HashSet<>();
            this.buildingBeans.set(ingBeans);
        }

        // 检测循环依赖
        if (ingBeans.contains(beanName)) {
            throw new Exception(beanName + " 循环依赖！" + ingBeans);
        }

        // 记录正在创建的Bean
        ingBeans.add(beanName);

        Class<?> beanClass = beanDefinition.getBeanClass();
        if(beanClass !=null){
            if(StringUtils.isBlank(beanDefinition.getFactoryMethodName())){
                // 构造方法来构造对象
                instance = this.createInstanceByConstructor(beanDefinition);
            }else {
                // 静态工厂方法
                instance = this.createInstanceByStaticFactoryMethod(beanDefinition);
            }
        }else{
            // 工厂bean方式来构造对象
            instance = this.createInstanceByFactoryBean(beanDefinition);
        }

        // 创建好实例后，移除创建中记录
        ingBeans.remove(beanName);

        //  给入属性依赖
        this.setPropertyDIValues(beanDefinition, instance);

        //应用bean初始化前的处理
        instance=this.applyPostProcessBeforeInitialization(instance,beanName);

        // 执行初始化方法
        this.doInit(beanDefinition,instance);

        //应用bean初始化后的处理
        instance=this.applyPostProcessAfterInitialization(instance,beanName);

        if(beanDefinition.isSingleton()){
            beanMap.put(beanName,instance);
        }

        return instance;
    }

    //应用bean初始化前的处理
    private Object applyPostProcessBeforeInitialization(Object bean, String beanName) throws Throwable {
        for (BeanPostProcessor beanPostProcessor:beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
        }
        return bean;
    }

    //应用bean初始化后的处理
    private Object applyPostProcessAfterInitialization(Object bean, String beanName) throws Throwable {
        for (BeanPostProcessor beanPostProcessor:beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
        }
        return bean;
    }

    private void setPropertyDIValues(BeanDefinition beanDefinition, Object instance) throws Throwable {
        if(CollectionUtils.isEmpty(beanDefinition.getPropertyValues())){
            return ;
        }

        for (PropertyValue pv : beanDefinition.getPropertyValues()) {
            if (StringUtils.isBlank(pv.getName())) {
                continue;
            }
            Class<?> clazz = instance.getClass();
            Field p = clazz.getDeclaredField(pv.getName());
            p.setAccessible(true);//设置可访问权限，即使没有get/set 也可以赋值

            Object rv = pv.getValue();
            Object v = null;
            if (rv == null) {
                v = null;
            } else if (rv instanceof BeanReference) {
                v = this.doGetBean(((BeanReference) rv).getBeanName());
            } else if (rv instanceof Object[]) {
                // TODO 处理集合中的bean引用
            } else if (rv instanceof Collection) {
                // TODO 处理集合中的bean引用
            } else if (rv instanceof Properties) {
                // TODO 处理properties中的bean引用
            } else if (rv instanceof Map) {
                // TODO 处理Map中的bean引用
            } else {
                v = rv;
            }
            p.set(instance, v);

        }
    }

    /**
     * 执行初始化方法
     * @param beanDefinition
     * @param instance
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void doInit(BeanDefinition beanDefinition, Object instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if(StringUtils.isNotBlank(beanDefinition.getInitMethodName())){
            Method method = instance.getClass().getMethod(beanDefinition.getInitMethodName(), new Class<?>[]{});
            method.invoke(instance,new Object[]{});
        }

    }

    /**
     * 构造方法来构造对象
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object createInstanceByConstructor(BeanDefinition beanDefinition) throws Throwable {
        try {
            Object[] args = this.getConstructorArgumentValues(beanDefinition);//获取构造参数
            if(args !=null){
                // 构造方法有参数
                // 决定构造方法
                return this.determineConstructor(beanDefinition,args).newInstance(args);
            }
//            return beanDefinition.getBeanClass().newInstance();//此方法在jdk9中已不推荐使用
            return beanDefinition.getBeanClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("创建bean的实例异常,beanDefinition：" +beanDefinition+"---"+e);
            throw e;
        }
    }

    /**
     * 决定构造方法
     * @param bd
     * @param args
     * @return
     * @throws Exception
     */
    private Constructor<?> determineConstructor(BeanDefinition bd, Object[] args) throws Exception {
        Constructor<?> ct = null;
        if (args == null) {
            return bd.getBeanClass().getConstructor(null);
        }

        // 对于原型bean,从第二次开始获取bean实例时，可直接获得第一次缓存的构造方法。
        ct=bd.getConstgructor();
        if(ct !=null){
            return ct;
        }


        // 根据参数类型获取精确匹配的构造方法
        Class<?>[] paramTypes= new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i]=args[i].getClass();
        }
        try {
            ct=bd.getBeanClass().getConstructor(paramTypes);
        } catch (Exception e) {
            // 这个异常不需要处理
        }

        if(ct == null){
            // 没有精确参数类型匹配的，则遍历匹配所有的构造方法
            // 判断逻辑：先判断参数数量，再依次比对形参类型与实参类型
            out:for (Constructor constructor:bd.getBeanClass().getConstructors()) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if(parameterTypes.length==args.length){
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if(!parameterTypes[i].isAssignableFrom(args[i].getClass())){
                            continue out;
                        }
                    }
                    ct=constructor;
                    break out;
                }
            }
        }

        if(ct != null){
            // 对于原型bean,可以缓存找到的构造方法，方便下次构造实例对象。在BeanDefinfition中获取设置所用构造方法的方法。
            // 同时在上面增加从beanDefinition中获取的逻辑。
            if(bd.isPrototype()){
                bd.setConstructor(ct);
            }
            return ct;
        }else{
            throw new Exception("不存在对应的构造方法 "+bd);
        }
    }

    /**
     * 静态工厂方法
     * @param beanDefinition
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object createInstanceByStaticFactoryMethod(BeanDefinition beanDefinition) throws Throwable {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object[] args =this.getConstructorArgumentValues(beanDefinition);//获取构造参数
//        Method method = beanClass.getMethod(beanDefinition.getFactoryMethodName(), new Class<?>[]{});
        //决定工厂方法
        Method method=this.determineFactoryMethod(beanDefinition,args,null);
        return method.invoke(beanClass,args);
    }


    /**
     * 工厂bean方式来构造对象
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object createInstanceByFactoryBean(BeanDefinition beanDefinition) throws Throwable {
        Object factoryBean = this.doGetBean(beanDefinition.getFactoryBeanName());
        Object[] args =this.getConstructorArgumentValues(beanDefinition);//获取构造参数
//        Method method = factoryBean.getClass().getMethod(beanDefinition.getFactoryMethodName(), new Class<?>[]{});
        //决定工厂方法
        Method method=this.determineFactoryMethod(beanDefinition,args,factoryBean.getClass());
        return method.invoke(factoryBean,args);
    }

    /**
     * 决定工厂方法
     * @param bd
     * @param args
     * @param type
     * @return
     */
    private Method determineFactoryMethod(BeanDefinition bd, Object[] args,  Class<?> type) throws Exception {
        if(type == null){
            type = bd.getBeanClass();
        }

        String methodName = bd.getFactoryMethodName();
        if(args == null){
            return type.getMethod(methodName, null);
        }

        Method m = null;
        // 对于原型bean,从第二次开始获取bean实例时，可直接获得第一次缓存的构造方法。
        m = bd.getFactoryMethod();
        if(m != null){
            return m;
        }

        // 根据参数类型获取精确匹配的方法
        Class<?>[] paramTypes=new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i]=args[i].getClass();
        }
        try {
            m = type.getMethod(methodName, paramTypes);
        }catch (Exception e){
            // 这个异常不需要处理
        }

        if(m == null){
            // 没有精确参数类型匹配的，则遍历匹配所有的方法
            // 判断逻辑：先判断参数数量，再依次比对形参类型与实参类型
            out:for (Method method:type.getMethods()) {
                if (!method.getName().equals(methodName)) {
                    continue;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if( parameterTypes.length==args.length ){
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if(!parameterTypes[i].isAssignableFrom(args[i].getClass())){
                            continue out;
                        }
                        m = method;
                        break out;
                    }
                }
            }
        }

        if (m != null) {
            // 对于原型bean,可以缓存找到的方法，方便下次构造实例对象。在BeanDefinfition中获取设置所用方法的方法。
            // 同时在上面增加从beanDefinition中获取的逻辑。
            if (bd.isPrototype()) {
                bd.setFactoryMethod(m);
            }
            return m;
        } else {
            throw new Exception("不存在对应的构造方法！" + bd);
        }

    }

    @Override
    public void close() throws IOException {
        // 执行单例实例的销毁方法
        for (Map.Entry<String,BeanDefinition> entry:this.beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();

            if(beanDefinition.isSingleton() && StringUtils.isNotBlank(beanDefinition.getInitMethodName())){
                Object instance = this.beanMap.get(beanName);
                try {
                    Method  method = instance.getClass().getMethod(beanDefinition.getDestroyMethodName(), new Class<?>[]{});
                    method.invoke(instance,new Object[]{});
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("执行bean[" + beanName + "] " + beanDefinition + " 的 销毁方法异常！--"+e);
                }

            }

        }

    }

    private Object[] getConstructorArgumentValues(BeanDefinition bd) throws Throwable {
        return this.getRealValues(bd.getConstructorArgumentValues());
    }

    private Object[] getRealValues(List<?> defs) throws Throwable {
        if(CollectionUtils.isEmpty(defs)){
            return null;
        }
        Object[] values=new Object[defs.size()];
        int i=0;
        Object value=null;
        for (Object obj :defs) {
            if (obj==null){
                value=null;
            }else if(obj instanceof  BeanReference){
                value=this.doGetBean(((BeanReference) obj).getBeanName());
            }else if(obj instanceof Object[]){
                // TODO 处理数组中的bean引用
            }else if (obj instanceof Collection) {
                // TODO 处理集合中的bean引用
            } else if (obj instanceof Properties) {
                // TODO 处理properties中的bean引用
            } else if (obj instanceof Map) {
                // TODO 处理Map中的bean引用
            } else {
                value=obj;
            }
            values[i++]=value;
        }
        return values;
    }
}

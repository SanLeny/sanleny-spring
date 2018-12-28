package cn.sanleny.spring.beans;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;
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
    public Object getBean(String beanName) throws Exception {
        return this.doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName,"beanName不能为空");

        Object instance=beanMap.get(beanName);
        if(null != instance){
            return instance;
        }
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        Objects.requireNonNull(beanDefinition,"beanDefinition不能为空");

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
        // 执行初始化方法
        this.doInit(beanDefinition,instance);

        if(beanDefinition.isSingleton()){
            beanMap.put(beanName,instance);
        }

        return instance;
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
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private Object createInstanceByConstructor(BeanDefinition beanDefinition) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try {
//            return beanDefinition.getBeanClass().newInstance();//此方法在jdk9中已不推荐使用
            return beanDefinition.getBeanClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("创建bean的实例异常,beanDefinition：" +beanDefinition+"---"+e);
            throw e;
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
    private Object createInstanceByStaticFactoryMethod(BeanDefinition beanDefinition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Method method = beanClass.getMethod(beanDefinition.getFactoryMethodName(), new Class<?>[]{});
        return method.invoke(beanClass,new Object[]{});
    }

    /**
     * 工厂bean方式来构造对象
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    private Object createInstanceByFactoryBean(BeanDefinition beanDefinition) throws Exception {
        Object factoryBean = this.doGetBean(beanDefinition.getFactoryBeanName());
        Method method = factoryBean.getClass().getMethod(beanDefinition.getFactoryMethodName(), new Class<?>[]{});
        return method.invoke(factoryBean,new Object[]{});
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

    private Object[] getConstructorArgumentValues(BeanDefinition bd) throws Exception {
        return this.getRealValues(bd.getConstructorArgumentValues());
    }

    private Object[] getRealValues(List<?> defs) throws Exception {
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

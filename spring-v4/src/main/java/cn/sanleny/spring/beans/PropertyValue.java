package cn.sanleny.spring.beans;

/**
 * 属性依赖定义
 * @Author: LG
 * @Date: 2018-12-28
 * @Description: cn.sanleny.spring.beans
 * @Version: 1.0
 */
public class PropertyValue {

    private String name;

    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

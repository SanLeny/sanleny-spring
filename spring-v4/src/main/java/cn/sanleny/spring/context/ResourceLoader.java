package cn.sanleny.spring.context;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public interface ResourceLoader {

    Resource getResource(String location);

}

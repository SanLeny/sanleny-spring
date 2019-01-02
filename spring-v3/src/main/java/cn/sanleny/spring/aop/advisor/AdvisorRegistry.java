package cn.sanleny.spring.aop.advisor;

import java.util.List;

/**
 * advisor 注册
 * @Author: sanleny
 * @Date: 2019-01-02
 * @Description: cn.sanleny.spring.aop.advisor
 * @since v3
 */
public interface AdvisorRegistry {

    void registryAdvisor(Advisor advisor);

    List<Advisor> getAdvisors();
}

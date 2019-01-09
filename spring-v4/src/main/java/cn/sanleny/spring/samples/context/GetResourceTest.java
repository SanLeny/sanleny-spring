package cn.sanleny.spring.samples.context;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.samples.context
 * @Version: 1.0
 */
public class GetResourceTest {

    public static void main(String[] args) throws IOException {
        Enumeration<URL> enu = GetResourceTest.class.getClassLoader().getResources("cn/sanleny/spring/samples/CBean.class");
        while (enu.hasMoreElements()) {
            System.out.println(enu.nextElement());
        }

        ClassPathScanningCandidateComponentProvider scan = new ClassPathScanningCandidateComponentProvider(true);
        scan.findCandidateComponents("cn/sanleny/spring");
    }
}

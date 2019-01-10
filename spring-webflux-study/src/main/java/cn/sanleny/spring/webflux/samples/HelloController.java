package cn.sanleny.spring.webflux.samples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: sanleny
 * @Date: 2019-01-10
 * @Description: cn.sanleny.spring.webflux.samples
 * @Version: 1.0
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String handle() {
        return "Hello WebFlux";
    }
}

package cn.sanleny.spring.webflux.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: sanleny
 * @Date: 2019-01-10
 * @Description: cn.sanleny.spring.webflux.thymeleaf
 * @Version: 1.0
 */
@Controller
public class ThymeleafController {

    @GetMapping("/thymeleaf")
    public String handle(String name, Model model) {
        model.addAttribute("name","sanleny");
        return "hello";
    }

}

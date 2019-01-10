package cn.sanleny.spring.webflux.samples;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @Author: sanleny
        * @Date: 2019-01-10
        * @Description: cn.sanleny.spring.webflux
        * @Version: 1.0
        */
@Configuration
@EnableWebFlux
public class MyWebConfig implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//         显式地完全启用它们的日志记录
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/public", "classpath:/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
}

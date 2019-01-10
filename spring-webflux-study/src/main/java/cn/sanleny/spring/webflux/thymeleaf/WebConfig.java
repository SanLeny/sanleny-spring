package cn.sanleny.spring.webflux.thymeleaf;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @Author: sanleny
 * @Date: 2019-01-10
 * @Description: cn.sanleny.spring.webflux.thymeleaf
 * @Version: 1.0
 */
@Configuration
public class WebConfig implements WebFluxConfigurer, ApplicationContextAware {


    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//         显式地完全启用它们的日志记录
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 配置：注册ThymeleafReactiveViewResolver
        registry.viewResolver(this.applicationContext.getBean(ThymeleafReactiveViewResolver.class));

    }

    /* WebFlux 集成 Thymeleaf 集成需要的三个bean */

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/templates/thymeleaf/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringWebFluxTemplateEngine webFluxTemplateEngine() {
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver thymeleafViewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(webFluxTemplateEngine());
        return viewResolver;
    }

}

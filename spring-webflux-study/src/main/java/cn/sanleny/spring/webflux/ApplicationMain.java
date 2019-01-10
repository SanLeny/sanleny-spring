package cn.sanleny.spring.webflux;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.JettyHttpHandlerAdapter;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.http.server.HttpServer;

import javax.servlet.Servlet;
import java.io.File;
import java.util.Scanner;

/**
 * spring webflux 启动类
 * @Author: sanleny
 * @Date: 2019-01-10
 * @Description: cn.sanleny.spring.webflux
 * @Version: 1.0
 */
@Configuration
//@ComponentScan("cn.sanleny.spring.webflux.samples")
@ComponentScan("cn.sanleny.spring.webflux.thymeleaf")
@EnableWebFlux
public class ApplicationMain {

    private static final int port =9000;

    public static void main(String args[]) throws Exception {
        // 1、创建ApplicationContext
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationMain.class);

        // 2、构建HttpHandler(http请求的处理器)
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();

//        tomcatStart(context,handler);//TODO 有点问题，待处理！！！
//        reactorStart(context,handler);
        jettyStart(context,handler);

    }

    public static void tomcatStart(AnnotationConfigApplicationContext context,HttpHandler handler) throws Exception {
        // 3、构建与底层http服务器程序API适配的 TomcatHttpHandlerAdapter
        Servlet servlet = new TomcatHttpHandlerAdapter(handler);

        // 4、开启服务
        Tomcat server = new Tomcat();
        File base = new File(System.getProperty("java.io.tmpdir"));
        System.out.println(base.getAbsolutePath());
        Context rootContext = server.addContext("", base.getAbsolutePath());
        Tomcat.addServlet(rootContext, "main", servlet);
        rootContext.addServletMappingDecoded("/", "main");

//        Host host=new StandardHost();
//        server.setHost(host);
        server.setPort(port);
        server.start();

        System.out.println(">>>> tomcat webflux start...");

        // 5、保持程序不关闭，来接收处理web请求
        // while (true)
        // ;
        // 5、也可提供一个用户交互来控制程序结束
        Scanner sc = new Scanner(System.in);
        while (!"s".equals(sc.nextLine()))
            ;
        sc.close();
        context.close();
        server.stop();
        System.out.println(">>>> tomcat webflux stop...");
        System.exit(0);
    }


    public static void reactorStart(AnnotationConfigApplicationContext context,HttpHandler handler){
        // 3、构建与底层http服务器程序API适配的ReactorHttpHandlerAdapter
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        // 4、开启http服务
        HttpServer.create().port(port).handle(adapter).bind().block();
        System.out.println(">>>> reactor webflux start...");

        // 5、保持程序不关闭，来接收处理web请求
        // while (true)
        // ;
        // 5、也可提供一个用户交互来控制程序结束
        Scanner sc = new Scanner(System.in);
        while (!"s".equals(sc.nextLine()))
            ;
        sc.close();
        context.close();
        System.out.println(">>>> reactor webflux stop...");
    }


    public static void jettyStart(AnnotationConfigApplicationContext context,HttpHandler handler) throws Exception {
        Servlet servlet = new JettyHttpHandlerAdapter(handler);

        Server server = new Server();
        ServletContextHandler contextHandler = new ServletContextHandler(server, "");
        contextHandler.addServlet(new ServletHolder(servlet), "/");
        contextHandler.start();

        ServerConnector connector = new ServerConnector(server);
//        connector.setHost(host);
        connector.setPort(port);
        server.addConnector(connector);
        server.start();
        System.out.println(">>>> jetty webflux start...");
        Scanner sc = new Scanner(System.in);
        while (!"s".equals(sc.nextLine()))
            ;
        sc.close();
        context.close();
        server.stop();
        System.out.println(">>>> jetty webflux stop...");
        System.exit(0);
    }

}

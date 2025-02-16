package com.nx.nxapigateway;

import com.nx.nxapi.provider.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * nxapi gateway application
 *
 * @author nx-xn2002
 */
@SpringBootApplication
@EnableDubbo
@Slf4j
public class NxapiGatewayApplication {
    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NxapiGatewayApplication.class, args);
        NxapiGatewayApplication application = context.getBean(NxapiGatewayApplication.class);
        log.info("启动成功");
        String nixiang = application.doSayHello("nixiang");
        log.info("调用结果:{}", nixiang);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes().build();
//    }
}

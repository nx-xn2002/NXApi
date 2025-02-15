package com.nx.nxapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * nxapi gateway application
 *
 * @author nx-xn2002
 */
@SpringBootApplication
public class NxapiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NxapiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/baidu")
                        .uri("https://cn.bing.com/"))
                .route("host_route", r -> r.path("/bing")
                        .uri("https://www.nxcoding.com"))
                .build();
    }
}

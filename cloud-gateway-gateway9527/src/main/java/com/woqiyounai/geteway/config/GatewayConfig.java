package com.woqiyounai.geteway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();
        RouteLocatorBuilder.Builder route = routes.route("path_route_woqiyounai",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei"));
        return route.build();
    }
}

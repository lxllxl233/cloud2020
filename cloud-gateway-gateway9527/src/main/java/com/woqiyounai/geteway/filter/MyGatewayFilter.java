package com.woqiyounai.geteway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Slf4j
@Component
public class MyGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("****全局过滤器****" + ZonedDateTime.now());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (null == uname){
            log.info("用户名为空");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }else {
            return chain.filter(exchange);
        }
    }

    //加载过滤器的顺序，顺序越小优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}

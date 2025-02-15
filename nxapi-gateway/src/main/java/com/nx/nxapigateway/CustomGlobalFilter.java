package com.nx.nxapigateway;

import cn.hutool.json.JSONObject;
import com.nx.demoapisdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 全局过滤
 *
 * @author nx-xn2002
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private final static List<String> IP_WHITE_LIST = List.of("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        //1. 请求日志
        log.info("请求唯一标识: {}", request.getId());
        log.info("请求路径: {}", request.getPath().value());
        log.info("请求方法: {}", request.getMethod());
        log.info("请求参数: {}", request.getQueryParams());
        log.info("请求参数: {}", request.getHeaders().getFirst("body"));
        log.info("请求来源地址: {}", request.getLocalAddress().getHostString());
        //2. 黑白名单
        String sourceAddress = request.getLocalAddress().getHostString();
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            return handleNoAuth(response);
        }
        //3. 用户鉴权（判断 AK、SK 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        //TODO: 查数据库这里是否已经分配给用户
        if (!accessKey.equals("123456")) {
            return handleNoAuth(response);
        }
        if (Long.parseLong(nonce) > 10000) {
            return handleNoAuth(response);
        }
        //时间不超过5分钟
        Long FIVE_MINUTES = 5 * 60 * 1000L;
        if (Long.parseLong(timestamp) + FIVE_MINUTES < System.currentTimeMillis()) {
            return handleNoAuth(response);
        }
        //TODO: 这里要查数据库获取secretKey
        String gennedSign = SignUtils.genSign(body, "123456");
        if (!sign.equals(gennedSign)) {
            return handleNoAuth(response);
        }
        //4. 请求的模拟接口是否存在
        //TODO:从数据库中查询模拟接口是否存在，以及请求方法是否匹配
        //5.请求转发、调用模拟接口（关键）
        Mono<Void> filter = chain.filter(exchange);
        //7. 响应日志
        return handleResponse(exchange, chain);
    }

    /**
     * 处理响应
     *
     * @param exchange exchange
     * @param chain    chain
     * @return {@link Mono }<{@link Void }>
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatusCode statusCode = originalResponse.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                //降级处理返回数据
                return chain.filter(exchange);
            }
            //装饰，增强能力
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    log.info("body instanceof Flux: {}", (body instanceof Flux<? extends DataBuffer>));
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        //往返回值里写数据
                        return super.writeWith(
                                fluxBody.map(dataBuffer -> {
                                    // TODO:调用成功，接口调用次数加一
                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    //释放掉内存
                                    DataBufferUtils.release(dataBuffer);
                                    //构建日志
                                    String data = new String(content, StandardCharsets.UTF_8);
                                    //打印日志
                                    log.info("响应结果:{}", data);
                                    return bufferFactory.wrap(content);
                                }));
                    } else {
                        //TODO:调用失败，返回一个规范的错误码
                        log.error("<-- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            //设置response对象为装饰过的
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        } catch (Exception e) {
            log.error("网关处理响应异常:{}", String.valueOf(e));
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
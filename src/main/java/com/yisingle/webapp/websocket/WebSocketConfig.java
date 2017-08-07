package com.yisingle.webapp.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements
        WebSocketConfigurer {

    public WebSocketConfig() {
    }


    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler(), "/websck").addInterceptors(new HandshakeInterceptor());

        System.out.println("registed!");
        // registry.addHandler(systemWebSocketHandler(), "/sockjs/websck").addInterceptors(new HandshakeInterceptor())
        //        .withSockJS();

        registry.addHandler(passagerWebSocketHandler(), "/passenger/websokcet").addInterceptors(new HandshakeInterceptor());

    }


    @Bean
    public WebSocketHandler systemWebSocketHandler() {
        //return new InfoSocketEndPoint();
        SystemWebSocketHandler handler = new SystemWebSocketHandler();
        System.out.println("测试代码SystemWebSocketHandler=====--------"+handler);
        return handler;
    }


    @Bean
    public WebSocketHandler passagerWebSocketHandler() {
        //return new InfoSocketEndPoint();
        PassagerWebSocketHandler handler = new PassagerWebSocketHandler();
        System.out.println("测试代码PassagerWebSocketHandler=====--------"+handler);
        return handler;
    }


}


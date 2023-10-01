package com.capstone.capstone.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebsocketSecurityInterceptor websocketSecurityInterceptor;
    private final ChatErrorHandler chatErrorHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                //.addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/ws")
                //.addInterceptors(new CustomHandshakeInterceptor())
                .setAllowedOriginPatterns("*");
        registry.setErrorHandler(chatErrorHandler);

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub","/queue"); //queue, topic. 서버에서 클라이언트로 전송하는
        registry.setApplicationDestinationPrefixes("/pub");//send. 클라이언트에서 서버로 전송받는
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(websocketSecurityInterceptor);
    }

    /*@Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(this::customWebSocketHandlerDecorator);
    }

    @Bean
    public WebSocketHandlerDecorator customWebSocketHandlerDecorator(WebSocketHandler webSocketHandler) {
        return new CustomWebSocketHandlerDecorator(webSocketHandler);
    }*/

}
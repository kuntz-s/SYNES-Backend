package com.synes.config.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    //ws://localhost:8080/stomp-endpoint
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp-endpoint/*");
        registry.addEndpoint("/stomp-endpoint/*").withSockJS();
                /*.setHandshakeHandler(new DefaultHandshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();*/
        //registry.addEndpoint("/stomp-endpoint").setHandshakeHandler(new DefaultHandshakeHandler());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/specific");
        registry.setApplicationDestinationPrefixes("/app");
    }


}

package orme.dominic.chatexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import orme.dominic.chatexample.config.handler.HeartbeatHandler;
import orme.dominic.chatexample.config.handler.MyTextHandler;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textHandler(),"/chat").setAllowedOrigins("*");
        registry.addHandler(heartbeatHandler(), "/heartbeat").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler textHandler() {
        return new MyTextHandler();
    }

    @Bean
    public WebSocketHandler heartbeatHandler() {
        return new HeartbeatHandler();
    }
}

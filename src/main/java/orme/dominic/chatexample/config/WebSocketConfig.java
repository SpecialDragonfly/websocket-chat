package orme.dominic.chatexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import orme.dominic.chatexample.config.handler.MyTextHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textHandler("1"),"/chat-example").setAllowedOrigins("*");
        registry.addHandler(textHandler("2"),"/chat-example/").setAllowedOrigins("*");
        registry.addHandler(textHandler("3"),"/chat-example/chat-example").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler textHandler(String value) {
        return new MyTextHandler(value);
    }
}

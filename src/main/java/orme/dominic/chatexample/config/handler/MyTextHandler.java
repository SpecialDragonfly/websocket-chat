package orme.dominic.chatexample.config.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

public class MyTextHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.printf("Connection established on session: %s%n", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        String payload = message.getPayload();
        Thread.sleep(1000); // simulated delay
        session.sendMessage(new TextMessage("Hello, " + HtmlUtils.htmlEscape(payload) + "!"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.printf("Exception occurred: %s on session: %s%n", exception.getMessage(), session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.printf("Connection closed on session: %s with status: %s%n", session.getId(), closeStatus.getCode());
    }
}

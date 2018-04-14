package ru.slisenko.message_system.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.slisenko.message_system.app.FrontendService;
import ru.slisenko.message_system.app.MessageSystemContext;
import ru.slisenko.message_system.db.AddMessage;
import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgSystem.Address;
import ru.slisenko.message_system.msgSystem.MessageSystem;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
public class ChatController implements FrontendService {
    private final BlockingQueue<ChatMessage> queueForRender = new LinkedBlockingQueue<>(10);
    @Autowired @Qualifier("msgSystemContext")
    private MessageSystemContext context;
    @Autowired @Qualifier("frontAddress")
    private Address address;

    @Override
    @PostConstruct
    public void init() {
        context.getMessageSystem().addAddressee(this);
        //context.getMessageSystem().start();
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        context.getMessageSystem().sendMessage(new AddMessage(getAddress(), context.getDbAddress(), chatMessage));
        try {
            return queueForRender.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @Override
    public void putChatMessageInQueueForRender(ChatMessage chatMessage) {
        try {
            queueForRender.put(chatMessage);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}

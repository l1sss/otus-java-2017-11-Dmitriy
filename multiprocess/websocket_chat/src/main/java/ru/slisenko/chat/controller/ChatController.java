package ru.slisenko.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.slisenko.message_server.app.FrontendService;
import ru.otus.slisenko.message_server.app.MessageSystemContext;
import ru.otus.slisenko.message_server.app.MsgWorker;
import ru.otus.slisenko.message_server.channel.SocketMsgWorker;
import ru.otus.slisenko.message_server.messages.AddMessage;
import ru.otus.slisenko.message_server.messages.FrontPingMsg;
import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Address;
import ru.otus.slisenko.message_server.msgsystem.Message;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class ChatController implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private MessageSystemContext context;
    @Autowired @Qualifier("front")
    private Address address;
    @Autowired @Qualifier("worker")
    private SocketMsgWorker socketMsgWorker;

    @Autowired
    private SimpMessagingTemplate messageForRender;

    @PostConstruct
    @SuppressWarnings("InfiniteLoopStatement")
    public void init() {
        socketMsgWorker.init();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (true) {
                try {
                    Message msg = socketMsgWorker.take();
                    msg.exec(this);
                    logger.info("Message received from: " + msg.getFrom());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        });

        Message msg = new FrontPingMsg(address, null);
        socketMsgWorker.send(msg);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = chatMessage.getSender();
        headerAccessor.getSessionAttributes().put("username", username);
        logger.info(username + " joined");
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        addDate(chatMessage);
        socketMsgWorker.send(new AddMessage(getAddress(), context.getDbAddress(), chatMessage));
    }

    private void addDate(ChatMessage chatMessage) {
        String time = new SimpleDateFormat("HH:mm MMM yyyy").format(new Date());
        chatMessage.setDate(time);
    }

    @Override
    public void renderMessage(ChatMessage chatMessage) {
        messageForRender.convertAndSend("/topic/public", chatMessage);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MsgWorker getMsgWorker() {
        return socketMsgWorker;
    }

    @Override
    public void setContext(MessageSystemContext messageSystemContext) {
        context = messageSystemContext;
    }
}

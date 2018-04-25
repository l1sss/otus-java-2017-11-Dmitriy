package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.DbService;
import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Address;

public class AddMessage extends MsgToDb {
    private final ChatMessage chatMessage;

    public AddMessage(Address from, Address to, ChatMessage chatMessage) {
        super(from, to);
        this.chatMessage = chatMessage;
    }

    @Override
    public void exec(DbService dbService) {
        ChatMessage savedChatMessage = dbService.save(chatMessage);
        dbService.getMsgWorker().send(new AddMessageAnswer(getTo(), getFrom(), savedChatMessage));
    }
}

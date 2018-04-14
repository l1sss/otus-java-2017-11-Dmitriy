package ru.slisenko.message_system.db;

import ru.slisenko.message_system.app.DbService;
import ru.slisenko.message_system.app.MsgToDb;
import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgsystem.Address;

public class AddMessage extends MsgToDb {
    private final ChatMessage chatMessage;

    public AddMessage(Address from, Address to, ChatMessage chatMessage) {
        super(from, to);
        this.chatMessage = chatMessage;
    }

    @Override
    public void exec(DbService dbService) {
        ChatMessage savedChatMessage = dbService.save(chatMessage);
        dbService.getMS().sendMessage(new AddMessageAnswer(getTo(), getFrom(), savedChatMessage));
    }
}

package ru.slisenko.message_system.app;

import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgsystem.Addressee;

public interface DbService extends Addressee {

    void init();

    ChatMessage save(ChatMessage chatMessage);
}

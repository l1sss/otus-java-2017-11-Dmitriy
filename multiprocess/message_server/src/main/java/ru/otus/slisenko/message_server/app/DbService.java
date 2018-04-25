package ru.otus.slisenko.message_server.app;

import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Addressee;

public interface DbService extends Addressee, AutoCloseable {

    ChatMessage save(ChatMessage chatMessage);
}

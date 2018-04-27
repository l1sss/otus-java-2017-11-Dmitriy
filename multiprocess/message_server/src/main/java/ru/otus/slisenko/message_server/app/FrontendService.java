package ru.otus.slisenko.message_server.app;

import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Addressee;

import java.util.List;

public interface FrontendService extends Addressee {

    void renderMessage(ChatMessage chatMessage);

    void renderHistory(String username, List<ChatMessage> history);
}

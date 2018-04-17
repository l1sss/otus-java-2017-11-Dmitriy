package ru.slisenko.message_system.app;

import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgsystem.Addressee;

public interface FrontendService extends Addressee {

    void init();

    void renderMessage(ChatMessage chatMessage);
}

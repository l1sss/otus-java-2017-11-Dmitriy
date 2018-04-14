package ru.slisenko.message_system.app;

import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgSystem.Addressee;

public interface FrontendService extends Addressee {

    void init();

    void putChatMessageInQueueForRender(ChatMessage chatMessage);
}

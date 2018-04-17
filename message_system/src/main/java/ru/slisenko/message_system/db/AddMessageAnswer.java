package ru.slisenko.message_system.db;

import ru.slisenko.message_system.app.FrontendService;
import ru.slisenko.message_system.app.MsgToFrontend;
import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgsystem.Address;

public class AddMessageAnswer extends MsgToFrontend {
    private ChatMessage chatMessage;

    public AddMessageAnswer(Address from, Address to, ChatMessage chatMessage) {
        super(from, to);
        this.chatMessage = chatMessage;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.renderMessage(chatMessage);
    }
}

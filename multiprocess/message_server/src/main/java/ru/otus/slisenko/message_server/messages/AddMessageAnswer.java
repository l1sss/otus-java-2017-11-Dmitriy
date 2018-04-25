package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.FrontendService;
import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Address;

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

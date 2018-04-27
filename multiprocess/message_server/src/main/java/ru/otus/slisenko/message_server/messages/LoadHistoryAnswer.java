package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.FrontendService;
import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Address;

import java.util.List;

public class LoadHistoryAnswer extends MsgToFrontend {
    private final String username;
    private final List<ChatMessage> history;

    public LoadHistoryAnswer(Address from, Address to, String username, List<ChatMessage> history) {
        super(from, to);
        this.username = username;
        this.history = history;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.renderHistory(username, history);
    }
}

package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.DbService;
import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Address;

import java.util.List;

public class LoadHistory extends MsgToDb {
    private final String username;

    public LoadHistory(Address from, Address to, String username) {
        super(from, to);
        this.username = username;
    }

    @Override
    public void exec(DbService dbService) {
        List<ChatMessage> history = dbService.readAll();
        dbService.getMsgWorker().send(new LoadHistoryAnswer(getTo(), getFrom(), username, history));
    }
}

package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.MessageSystemContext;
import ru.otus.slisenko.message_server.msgsystem.Address;
import ru.otus.slisenko.message_server.msgsystem.Addressee;
import ru.otus.slisenko.message_server.msgsystem.Message;

public class PingMsgAnswer extends Message {
    private final MessageSystemContext context;

    public PingMsgAnswer(Address from, Address to, MessageSystemContext context) {
        super(from, to);
        this.context = context;
    }

    @Override
    public void exec(Addressee addressee) {
        addressee.setContext(context);
    }
}

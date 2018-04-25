package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.msgsystem.Address;
import ru.otus.slisenko.message_server.msgsystem.Addressee;
import ru.otus.slisenko.message_server.msgsystem.Message;

public class FrontPingMsg extends Message {

    public FrontPingMsg(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
    }
}

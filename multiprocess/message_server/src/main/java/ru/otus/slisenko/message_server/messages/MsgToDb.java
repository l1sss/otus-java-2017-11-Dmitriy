package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.DbService;
import ru.otus.slisenko.message_server.msgsystem.Address;
import ru.otus.slisenko.message_server.msgsystem.Addressee;
import ru.otus.slisenko.message_server.msgsystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDb extends Message {
    public MsgToDb(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DbService) {
            exec((DbService) addressee);
        }
    }

    public abstract void exec(DbService dbService);
}

package ru.slisenko.message_system.app;

import ru.slisenko.message_system.msgsystem.Address;
import ru.slisenko.message_system.msgsystem.Addressee;
import ru.slisenko.message_system.msgsystem.Message;

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

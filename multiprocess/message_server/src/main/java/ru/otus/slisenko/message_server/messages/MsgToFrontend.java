package ru.otus.slisenko.message_server.messages;

import ru.otus.slisenko.message_server.app.FrontendService;
import ru.otus.slisenko.message_server.msgsystem.Address;
import ru.otus.slisenko.message_server.msgsystem.Addressee;
import ru.otus.slisenko.message_server.msgsystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        } else {
            //todo error!
        }
    }

    public abstract void exec(FrontendService frontendService);
}

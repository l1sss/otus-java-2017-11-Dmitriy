package ru.slisenko.message_system.app;

import ru.slisenko.message_system.msgsystem.Address;
import ru.slisenko.message_system.msgsystem.Addressee;
import ru.slisenko.message_system.msgsystem.Message;

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

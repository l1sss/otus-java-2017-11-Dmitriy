package ru.slisenko.message_system.app;

import ru.slisenko.message_system.msgSystem.Address;
import ru.slisenko.message_system.msgSystem.Addressee;
import ru.slisenko.message_system.msgSystem.Message;

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

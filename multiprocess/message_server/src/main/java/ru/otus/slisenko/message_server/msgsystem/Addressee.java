package ru.otus.slisenko.message_server.msgsystem;

import ru.otus.slisenko.message_server.app.MessageSystemContext;
import ru.otus.slisenko.message_server.app.MsgWorker;

public interface Addressee {

    Address getAddress();

    MsgWorker getMsgWorker();

    void setContext(MessageSystemContext context);
}

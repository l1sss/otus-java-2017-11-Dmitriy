package ru.otus.slisenko.message_server.app;

import ru.otus.slisenko.message_server.msgsystem.Message;

import java.io.IOException;

public interface MsgWorker {

    void send(Message message);

    Message pool();

    @Blocks
    Message take() throws InterruptedException;

    void close() throws IOException;
}

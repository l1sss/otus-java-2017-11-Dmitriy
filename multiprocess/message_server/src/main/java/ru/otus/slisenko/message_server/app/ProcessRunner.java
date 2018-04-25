package ru.otus.slisenko.message_server.app;


import java.io.IOException;

/**
 * Created by tully.
 */
public interface ProcessRunner {

    void start(String command) throws IOException;

    void stop();

    String getOutput();
}
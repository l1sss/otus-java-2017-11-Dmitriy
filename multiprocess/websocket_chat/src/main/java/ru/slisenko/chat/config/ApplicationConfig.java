package ru.slisenko.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.slisenko.message_server.channel.SocketMsgWorker;
import ru.otus.slisenko.message_server.msgsystem.Address;

import java.io.IOException;
import java.net.Socket;

@Configuration
public class ApplicationConfig {
    @Value("${messageServer.host}")
    private String host;
    @Value("${messageServer.port}")
    private int port;

    @Bean("worker")
    public SocketMsgWorker socketMsgWorker() throws IOException {
        Socket socket = new Socket(host, port);
        return new SocketMsgWorker(socket);
    }

    @Bean("front")
    public Address frontAddress() {
        return new Address("front");
    }
}

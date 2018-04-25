package ru.otus.slisenko.dbservice.dao;

import ru.otus.slisenko.dbservice.executor.Executor;
import ru.otus.slisenko.dbservice.executor.ExecutorImpl;
import ru.otus.slisenko.message_server.model.ChatMessage;

import java.sql.Connection;

public class ChatMessageDAO {
    private static final String INSERT_QUERY = "INSERT INTO messages(content, date, sender) VALUES('%s', '%s', '%s');";
    private static final String SELECT_BY_ID = "SELECT * FROM messages WHERE id=%s;";

    private final Executor executor;

    public ChatMessageDAO(Connection connection) {
        executor = new ExecutorImpl(connection);
    }

    public long save(ChatMessage chatMessage) {
        String insert = String.format(INSERT_QUERY,
                chatMessage.getContent(), chatMessage.getDate(), chatMessage.getSender());
        return executor.execInsert(insert);
    }

    public ChatMessage load(long id) {
        return executor.execQuery(String.format(SELECT_BY_ID, id), resultSet -> {
            ChatMessage chatMessage = new ChatMessage();
            if (resultSet.next()) {
                chatMessage.setId(resultSet.getInt(1));
                chatMessage.setContent(resultSet.getString(2));
                chatMessage.setDate(resultSet.getString(3));
                chatMessage.setSender(resultSet.getString(4));
            }
            return chatMessage;
        });
    }
}

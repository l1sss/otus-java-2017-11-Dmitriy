package ru.otus.slisenko.dbservice.dao;

import ru.otus.slisenko.dbservice.executor.Executor;
import ru.otus.slisenko.dbservice.executor.ExecutorImpl;
import ru.otus.slisenko.message_server.model.ChatMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAO {
    private static final String INSERT_QUERY = "INSERT INTO messages(content, date, sender) VALUES(?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT * FROM messages WHERE id=%s;";
    private static final String SELECT_ALL = "SELECT * FROM messages;";

    private final Executor executor;

    public ChatMessageDAO(Connection connection) {
        executor = new ExecutorImpl(connection);
    }

    public long save(ChatMessage chatMessage) {
        return executor.execUpdate(INSERT_QUERY, statement -> {
           statement.setString(1, chatMessage.getContent());
           statement.setString(2, chatMessage.getDate());
           statement.setString(3, chatMessage.getSender());
        });
    }

    public ChatMessage load(long id) {
        return executor.execQuery(String.format(SELECT_BY_ID, id), resultSet -> {
            ChatMessage chatMessage = new ChatMessage();
            if (resultSet.next()) {
                chatMessage = buildChatMessage(resultSet);
            }
            return chatMessage;
        });
    }

    public List<ChatMessage> loadAll() {
        List<ChatMessage> messages = new ArrayList<>();
        executor.execQuery(SELECT_ALL, resultSet -> {
           while (resultSet.next()) {
               ChatMessage chatMessage = buildChatMessage(resultSet);
               messages.add(chatMessage);
           }
           return messages;
        });
        return messages;
    }

    private ChatMessage buildChatMessage(ResultSet resultSet) {
        try {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(resultSet.getInt(1));
            chatMessage.setContent(resultSet.getString(2));
            chatMessage.setDate(resultSet.getString(3));
            chatMessage.setSender(resultSet.getString(4));
            return chatMessage;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

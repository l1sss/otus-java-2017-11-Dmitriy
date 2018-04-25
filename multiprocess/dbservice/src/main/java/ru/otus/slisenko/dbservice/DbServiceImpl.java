package ru.otus.slisenko.dbservice;

import ru.otus.slisenko.dbservice.dao.ChatMessageDAO;
import ru.otus.slisenko.message_server.app.DbService;
import ru.otus.slisenko.message_server.app.MessageSystemContext;
import ru.otus.slisenko.message_server.app.MsgWorker;
import ru.otus.slisenko.message_server.channel.SocketMsgWorker;
import ru.otus.slisenko.message_server.messages.DbPingMsg;
import ru.otus.slisenko.message_server.model.ChatMessage;
import ru.otus.slisenko.message_server.msgsystem.Address;
import ru.otus.slisenko.message_server.msgsystem.Message;
import ru.otus.slisenko.message_server.util.PropertiesHelper;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbServiceImpl implements DbService {
    private static final Logger logger = Logger.getLogger(DbServiceImpl.class.getName());

    private static final String HOST;
    private static final int MSG_SYSTEM_SERVER_PORT;
    static {
        Properties properties = PropertiesHelper.getProperties("dbservice.properties");
        HOST = properties.getProperty("messageServer.host");
        MSG_SYSTEM_SERVER_PORT = Integer.parseInt(properties.getProperty("messageServer.port"));
    }

    private final Connection connection;
    private final ChatMessageDAO dao;
    private final Address address;
    private final SocketMsgWorker socketMsgWorker;

    private MessageSystemContext context;

    public DbServiceImpl() throws IOException {
        connection = ConnectionHelper.getConnection();
        dao = new ChatMessageDAO(connection);
        address = new Address("dbservice");
        Socket socket = new Socket(HOST, MSG_SYSTEM_SERVER_PORT);
        socketMsgWorker = new SocketMsgWorker(socket);
        socketMsgWorker.init();
    }

    public static void main(String[] args) throws IOException {
        new DbServiceImpl().start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (true) {
                try {
                    Message msg = socketMsgWorker.take(); //blocks
                    msg.exec(this);
                    logger.info("Message received from: " + msg.getFrom());
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
        });

        Message dbPingMsg = new DbPingMsg(address, null);
        socketMsgWorker.send(dbPingMsg);
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        long id = dao.save(chatMessage);
        return dao.load(id);
    }

    @Override
    public void close() throws Exception {
        connection.close();
        logger.info("Connection closed. Bye!");
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MsgWorker getMsgWorker() {
        return socketMsgWorker;
    }

    @Override
    public void setContext(MessageSystemContext messageSystemContext) {
        context = messageSystemContext;
    }
}

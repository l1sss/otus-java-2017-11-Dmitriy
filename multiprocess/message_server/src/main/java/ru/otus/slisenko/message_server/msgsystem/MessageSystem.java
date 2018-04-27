package ru.otus.slisenko.message_server.msgsystem;

import ru.otus.slisenko.message_server.app.Blocks;
import ru.otus.slisenko.message_server.app.MessageSystemContext;
import ru.otus.slisenko.message_server.app.MsgWorker;
import ru.otus.slisenko.message_server.channel.SocketMsgWorker;
import ru.otus.slisenko.message_server.messages.DbPingMsg;
import ru.otus.slisenko.message_server.messages.FrontPingMsg;
import ru.otus.slisenko.message_server.messages.PingMsgAnswer;
import ru.otus.slisenko.message_server.util.PropertiesHelper;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MessageSystem implements MessageSystemMBean {
    private static final Logger logger = Logger.getLogger(MessageSystem.class.getName());

    private static final int PORT;
    private static final int THREADS_NUMBER;
    private static final int STEP_TIME;
    static {
        Properties properties = PropertiesHelper.getProperties("msgsystem.properties");
        PORT = Integer.parseInt(properties.getProperty("msgsystem.port"));
        THREADS_NUMBER = Integer.parseInt(properties.getProperty("msgsystem.threads"));
        STEP_TIME = Integer.parseInt(properties.getProperty("msgsystem.step"));
    }

    private final ExecutorService executor;
    private final List<MsgWorker> workers;
    private final Map<Address, MsgWorker> clients;
    private final MessageSystemContext context;

    public MessageSystem() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
        clients = new HashMap<>();
        context = new MessageSystemContext();
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::processMessage);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                worker.addShutdownRegistration(() -> workers.remove(worker));
                workers.add(worker);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void processMessage() {
        while (true) {
           for (MsgWorker worker : workers) {
               Message msg = worker.pool();
               while (msg != null) {
                   if (msg.getClass().isAssignableFrom(FrontPingMsg.class)) {
                       Address sender = msg.getFrom();
                       logger.info("Ping message received from: " + sender);
                       context.setFrontAddress(sender);
                       clients.put(sender, worker);

                       sendCurrentContextToAllAddresses();
                   } else if (msg.getClass().isAssignableFrom(DbPingMsg.class)) {
                       Address sender = msg.getFrom();
                       logger.info("Ping message received from: " + sender);
                       context.setDbAddress(sender);
                       clients.put(sender, worker);

                       sendCurrentContextToAllAddresses();
                   } else {
                       Address destination = msg.getTo();
                       logger.info("Transit message to: " + destination);
                       MsgWorker msgWorker = clients.get(destination);
                       if (msgWorker != null) {
                           msgWorker.send(msg);
                       }
                   }
                   msg = worker.pool();
               }
           }
           try {
               Thread.sleep(STEP_TIME);
           } catch (InterruptedException e) {
               logger.log(Level.SEVERE, e.getMessage());
           }
        }
    }

    private void sendCurrentContextToAllAddresses() {
        for (Map.Entry<Address, MsgWorker> entry : clients.entrySet()) {
            MsgWorker worker = entry.getValue();
            worker.send(new PingMsgAnswer(null, entry.getKey(), context));
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            logger.info("Bye.");
        }
    }
}

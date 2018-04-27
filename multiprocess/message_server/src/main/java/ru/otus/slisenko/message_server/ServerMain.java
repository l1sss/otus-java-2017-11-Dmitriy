package ru.otus.slisenko.message_server;

import ru.otus.slisenko.message_server.msgsystem.MessageSystem;
import ru.otus.slisenko.message_server.runner.ProcessRunnerImpl;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String DBSERVICE_START_COMMAND = "java -jar ../dbservice/target/dbservice-1.0.jar";
    private static final String FRONT_START_COMMAND = "java -jar ../websocket_chat/target/websocket_chat-1.0.jar";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        startClient(executorService, DBSERVICE_START_COMMAND);
        //startClient(executorService, FRONT_START_COMMAND);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        MessageSystem server = new MessageSystem();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                logger.info("Start child process on command: " + command);
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
    }
}

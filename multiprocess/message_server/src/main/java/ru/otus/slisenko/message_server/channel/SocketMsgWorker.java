package ru.otus.slisenko.message_server.channel;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.slisenko.message_server.app.Blocks;
import ru.otus.slisenko.message_server.app.MsgWorker;
import ru.otus.slisenko.message_server.msgsystem.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketMsgWorker implements MsgWorker {
    private static final Logger logger = Logger.getLogger(SocketMsgWorker.class.getName());

    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;
    private final List<Runnable> shutdownRegistrations;

    public SocketMsgWorker(Socket socket) {
        this.socket = socket;
        shutdownRegistrations = new ArrayList<>();
        executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public Message pool() {
        return input.poll();
    }

    @Override
    @Blocks
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() {
        shutdownRegistrations.forEach(Runnable::run);
        shutdownRegistrations.clear();

        executor.shutdown();

        logger.info("Client socket is closed");
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    public void addShutdownRegistration(Runnable runnable) {
        this.shutdownRegistrations.add(runnable);
    }

    @Blocks
    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Message msg = output.take(); //blocks
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println(); //end of the message
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Blocks
    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    if (json.isEmpty())
                        continue;
                    Message msg = getMsgFromJSON(json);
                    if (msg != null)
                        input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            close();
        }
    }

    private static Message getMsgFromJSON(String json) throws ClassNotFoundException {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            String className = (String) jsonObject.get(Message.CLASS_NAME_VARIABLE);
            Class<?> msgClass = Class.forName(className);
            return (Message) new Gson().fromJson(json, msgClass);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Parsing error: " + e.getMessage());
            return null;
        }
    }
}

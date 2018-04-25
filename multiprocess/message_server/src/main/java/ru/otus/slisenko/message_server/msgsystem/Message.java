package ru.otus.slisenko.message_server.msgsystem;

/**
 * @author tully
 */
public abstract class Message {
    public static final String CLASS_NAME_VARIABLE = "className";

    private final String className;
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
        className = this.getClass().getName();
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}

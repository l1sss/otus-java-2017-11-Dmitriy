package ru.slisenko.message_system.msgSystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}

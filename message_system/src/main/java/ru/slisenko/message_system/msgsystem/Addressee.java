package ru.slisenko.message_system.msgsystem;

/**
 * @author tully
 */
public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}

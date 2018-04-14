package ru.slisenko.message_system.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.slisenko.message_system.msgsystem.Address;
import ru.slisenko.message_system.msgsystem.MessageSystem;

/**
 * Created by tully.
 */
@Component("msgSystemContext")
public class MessageSystemContext {
    private final MessageSystem messageSystem;
    @Autowired @Qualifier("frontAddress")
    private Address frontAddress;
    @Autowired @Qualifier("dbAddress")
    private Address dbAddress;

    public MessageSystemContext(@Autowired @Qualifier("msgsystem") MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }
}

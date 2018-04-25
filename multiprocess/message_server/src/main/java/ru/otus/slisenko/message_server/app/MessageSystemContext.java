package ru.otus.slisenko.message_server.app;

import ru.otus.slisenko.message_server.msgsystem.Address;

public class MessageSystemContext {
    private Address frontAddress;
    private Address dbAddress;

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

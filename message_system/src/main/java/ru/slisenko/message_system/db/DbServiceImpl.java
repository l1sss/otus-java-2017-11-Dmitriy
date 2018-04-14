package ru.slisenko.message_system.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.slisenko.message_system.app.DataRepository;
import ru.slisenko.message_system.app.DbService;
import ru.slisenko.message_system.app.MessageSystemContext;
import ru.slisenko.message_system.front.model.ChatMessage;
import ru.slisenko.message_system.msgsystem.Address;
import ru.slisenko.message_system.msgsystem.MessageSystem;

import javax.annotation.PostConstruct;

@Service("dbService")
public class DbServiceImpl implements DbService {
    @Autowired @Qualifier("dataRepository")
    private DataRepository dataRepository;
    @Autowired @Qualifier("dbAddress")
    private Address address;
    @Autowired @Qualifier("msgSystemContext")
    private MessageSystemContext context;

    @Override
    @PostConstruct
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return dataRepository.save(chatMessage);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}

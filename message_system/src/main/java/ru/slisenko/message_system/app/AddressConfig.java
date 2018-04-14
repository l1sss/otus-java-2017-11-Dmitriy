package ru.slisenko.message_system.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.slisenko.message_system.msgSystem.Address;

@Configuration
public class AddressConfig {

    @Bean("frontAddress")
    public Address frontAddress() {
        return new Address("frontAddress");
    }

    @Bean("dbAddress")
    public Address dbAddress() {
        return new Address("dbAddress");
    }
}

package ru.otus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;

@Configuration
public class MessageServerConfig {

    @Bean
    public MessageSystem getMessageSystem() {
        return new MessageSystemImpl();
    }


}

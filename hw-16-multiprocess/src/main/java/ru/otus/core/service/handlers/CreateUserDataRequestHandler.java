package ru.otus.core.service.handlers;

import org.springframework.stereotype.Service;
import ru.otus.app.common.Serializers;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.MessageType;
import ru.otus.messagesystem.RequestHandler;

import java.util.Optional;

@Service("CreateUserDataRequestHandler")
public class CreateUserDataRequestHandler implements RequestHandler {
    private final DBServiceUser dbService;

    public CreateUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = Serializers.deserialize(msg.getPayload(), User.class);
        dbService.saveUser(user);
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.CREATE_USER.getValue(), Serializers.serialize(user)));
    }

}

package ru.otus.core.service.handlers;

import org.springframework.stereotype.Service;
import ru.otus.app.common.Serializers;
import ru.otus.core.service.DBServiceUser;
import ru.otus.messagesystem.Message;
import ru.otus.messagesystem.MessageType;
import ru.otus.messagesystem.RequestHandler;

import java.util.Optional;

//@Service("GetUserDataRequestHandler")
public class GetUserDataRequestHandler implements RequestHandler {
    private final DBServiceUser dbService;

    public GetUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        long id = Serializers.deserialize(msg.getPayload(), Long.class);
//        String data = dbService.getUserData(id);
//        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.USER_DATA.getValue(), Serializers.serialize(data)));
        return null;
    }

}

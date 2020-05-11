package ru.otus.front;


import ru.otus.core.model.User;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);

    void saveUser(User user, Consumer<User> dataConsumer);
}


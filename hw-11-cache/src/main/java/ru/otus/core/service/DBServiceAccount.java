package ru.otus.core.service;

import ru.otus.core.model.Account;
import ru.otus.core.model.User;

import java.util.Optional;

public interface DBServiceAccount {

  long saveAccount(Account user);

  Optional<Account> getAccount(long id);

}

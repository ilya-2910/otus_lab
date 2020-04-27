package ru.otus.cachehw;

public enum CacheAction {

    PUT("put"),
    REMOVE("remove");

    private final String name;

    CacheAction(String name) {
        this.name = name;
    }
}

package ru.otus.core.model;

import ru.otus.jdbc.dao.Id;

public class Account {

    @Id
    private Long no;

    private String type;
    private Integer rest;

    public Account() {
    }

    public Account(Long no, String type, Integer rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public Long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public Integer getRest() {
        return rest;
    }
}

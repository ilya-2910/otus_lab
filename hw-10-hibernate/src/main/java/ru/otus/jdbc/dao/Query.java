package ru.otus.jdbc.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
class Query {
    private String query;
    private List<String> queryParams;
}

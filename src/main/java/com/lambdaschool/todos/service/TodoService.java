package com.lambdaschool.todos.service;

import com.lambdaschool.todos.model.Todo;

import java.util.List;

public interface TodoService {
    Todo update(Todo todo, long todoid);

    List<Todo> findAll();
}

package com.lambdaschool.todos.service;

import com.lambdaschool.todos.model.Todo;

public interface TodoService {
    Todo update(Todo todo, long todoid);

}

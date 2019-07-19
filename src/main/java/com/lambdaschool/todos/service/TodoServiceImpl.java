package com.lambdaschool.todos.service;

import com.lambdaschool.todos.model.Todo;
import com.lambdaschool.todos.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService {
    @Autowired
    private ToDoRepository todorepos;

    @Override
    public Todo update(Todo todo, long todoid) {
        Todo updateTodo = todorepos.findById(todoid).orElseThrow(() -> new EntityNotFoundException(Long.toString(todoid)));

        if (todo.getDescription() != null) {
            updateTodo.setDescription(todo.getDescription());
        }

        if (todo.getUser() != null) {
            updateTodo.setUser(todo.getUser());
        }

        return todorepos.save(updateTodo);
    }
}

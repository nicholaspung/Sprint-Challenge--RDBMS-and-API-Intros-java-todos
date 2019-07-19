package com.lambdaschool.todos.controller;

import com.lambdaschool.todos.model.Todo;
import com.lambdaschool.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodosController {
    @Autowired
    private TodoService todoService;

    @PutMapping(value = "/todoid/{todoid}")
    public ResponseEntity<?> updateTodo(@RequestBody Todo todo, @PathVariable long todoid) {
        return new ResponseEntity<>(todoService.update(todo, todoid), HttpStatus.OK);
    }

    // Testing
    @GetMapping(value = "/todos",
            produces = {"application/json"})
    public ResponseEntity<?> listAllQuotes()
    {
        List<Todo> allQuotes = todoService.findAll();
        return new ResponseEntity<>(allQuotes, HttpStatus.OK);
    }
    //
}

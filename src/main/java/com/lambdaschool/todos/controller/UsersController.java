package com.lambdaschool.todos.controller;

import com.lambdaschool.todos.model.Todo;
import com.lambdaschool.todos.model.User;
import com.lambdaschool.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User newuser) throws URISyntaxException {
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/mine")
    public ResponseEntity<?> getUserAndTodos() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/todo/{userid}")
    public ResponseEntity<?> addTodoToUser(@Valid @RequestBody Todo newtodo, @PathVariable long userid) throws URISyntaxException {
        userService.assign(newtodo, userid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/userid/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid) {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

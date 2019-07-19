package com.lambdaschool.todos.controller;

import com.lambdaschool.todos.model.Todo;
import com.lambdaschool.todos.model.User;
import com.lambdaschool.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService userService;

    // For testing purposes
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }
    //

    @PostMapping(produces = {"application/json"})
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

    @GetMapping(value = "/mine", produces = {"application/json"})
    public ResponseEntity<?> getUserAndTodos() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = userService.findUserByName(username);
            return new ResponseEntity<>(user.getTodos(), HttpStatus.OK);
        } else {
            String username = principal.toString();
            User user = userService.findUserByName(username);
            return new ResponseEntity<>(user.getTodos(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/todo/{userid}")
    public ResponseEntity<?> addTodoToUser(@Valid @RequestBody Todo newtodo, @PathVariable long userid) throws URISyntaxException {
        return new ResponseEntity<>(userService.assign(newtodo, userid), HttpStatus.OK);
    }

    @DeleteMapping(value = "/userid/{userid}", produces = {"application/json"})
    public ResponseEntity<?> deleteUser(@PathVariable long userid) {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

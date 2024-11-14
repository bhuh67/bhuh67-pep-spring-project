package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jni.User;
import org.springframework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.service.*;
import com.example.entity.*;
import com.example.exception.UserExistsException;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }



    //Handler for registering a new user
    //Returns the new user inserted given that the username isn't empty, does not already exist in the repository, 
    //and password is >4 characters. 
    //Duplicate username: Status 409 else: Status 400

    @PostMapping("/register")
    public ResponseEntity<Account> postNewUserHandler(@RequestBody Account account){
        Account registeredUser = accountService.registerAccount(account);
        if(registeredUser == null){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> HandleUserExists(UserExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    //Handler for verifying login given json account
    //Logged in: Status 200 Unsuccessful login: Status 401
    //Return a logged in user if successful.
    @PostMapping("/login")
    public ResponseEntity<Account> postUserLoginHandler(@RequestBody Account account){
        Account foundAccount = accountService.loginAccount(account);
        if (foundAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(200).body(foundAccount);
    }

    //Handler for making a new post given json message, excluding id
    //Successful if body isn't blank, isn't over 255 chars, postedBy User exists in repository
    //Successful Status: 200 Unsuccessful Status: 400
    //Return a json message including id
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessageHandler(@RequestBody Message message){
        Message postedMessage = messageService.postMessage(message);
        if(message == null){
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).body(postedMessage);
    }

    //Handler for getting all messages
    //Status: 200
    //Return a list of messages, empty if repository is empty
    @GetMapping("/messages")
    public List<Message> getAllMessagesHandler(){
        return new ArrayList<Message>();
    }

    //Handler for getting a message based on an id
    //Status: 200
    //Return message picked, if id doesn't match, return an empty response
    @GetMapping("/messages/{messageId}")
    public Message getMessageByIdHandler(@PathVariable int messageId){
        return null;
    }   

    //Handler for deleting a message based on id
    //Status: 200
    //Given the id, delete the message, and return the number of rows (entries) updated
    @DeleteMapping("/messages/{messageId}")
    public int deleteMessageByIdHandler(@PathVariable int messageId){
        return 0;
    }

    //Handler for updating a message based on id
    //Successful Status: 200, Failed status: 400
    //Given the id and new message, update if text is present and under 255 chars.
    //Return rows updated
    @PatchMapping("/messages/{messageId}")
    public int patchMessageByIdHandler(@PathVariable int messageId){
        return 0;
    }

    //Handler for getting all messages based on the user that posted them
    //Status: 200
    //Given the user's id, Return a list of all messages with that user's id in postedBy
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessageByUserHandler(@PathVariable int accountId){
        return new ArrayList<>();
    }

}

package com.example.service;

import java.util.List;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository){
        this.repository = repository;
    }

    public Message postMessage(Message message) throws JdbcSQLIntegrityConstraintViolationException{
        if(message.getMessageText().length() > 255 || message.getMessageText().isEmpty()){
            return null;
        }
        return repository.save(message);
    }

    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    public Message identifyMessage(int messageId) {
        return repository.findMessageByMessageId(messageId);
    }

    public Integer deleteMessage(int messageId) {
        Message foundMessage = repository.findMessageByMessageId(messageId);
        if(foundMessage != null){
            repository.delete(foundMessage);
            return 1;
        }
        return null;
    }

    public Integer updateMessage(int messageId, Message message) {
        if(message.getMessageText().isEmpty() || message.getMessageText().length() > 255 || identifyMessage(messageId) == null){
            return null;
        }
        else{
            Message updatedMessage = repository.findMessageByMessageId(messageId);
            updatedMessage.setMessageText(message.getMessageText());
            repository.save(updatedMessage);
            return 1;
        }
    }

    public List<Message> getUserMessages(int accountId) {
        return repository.getUserMessages(accountId);
    }
}

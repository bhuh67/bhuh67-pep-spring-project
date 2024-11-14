package com.example.service;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.UserExistsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository)
    {
        this.repository = repository;
    }   

    public Account registerAccount(Account account) throws UserExistsException{
        if(repository.findAccountByUsername(account.getUsername())!=null ){
            throw new UserExistsException();
        }
        if(account.getPassword().length() < 4){
            return null;
        }
        return repository.save(account);
    }

    public Account loginAccount(Account account) {
        Account loggedAccount = repository.findAccountByUsername(account.getUsername());
        if(loggedAccount == null)
        {
            //System.out.println("not found");
            return null;
        }
        if( loggedAccount.getPassword().equals(account.getPassword()))
        {
            return loggedAccount;
        }
        //System.out.println(account.getPassword() == loggedAccount.getPassword());
        return null;
    }

    public Account identifyAccount(Message message) {
        return repository.getById(message.getPostedBy());
    }
}

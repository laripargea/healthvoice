package com.example.demo.controller;

import java.util.List;

import javax.mail.MessagingException;

import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages/add/{email}")
    public Message save(@RequestBody Message message, @PathVariable String email) {
        return messageService.save(message, email);
    }

    @PostMapping("/messages/add/anonymous/{email}")
    public Message saveAnonymous(@RequestBody Message message, @PathVariable String email) throws MessagingException {
        return messageService.saveAnonymous(message, email);
    }

    @GetMapping("/messages/received/{email}")
    public List<Message> getMessagesByReceiver(@PathVariable("email") String email) {
        return messageService.getMessagesByReceiver(email);
    }

    @GetMapping("/messages/sent/{email}")
    public List<Message> getMessagesBySender(@PathVariable("email") String email) {
        return messageService.getMessagesBySender(email);
    }

    @GetMapping("/messages/admin")
    public List<Message> getMessagesAdmin() {
        return messageService.getMessagesAdmin();
    }
}
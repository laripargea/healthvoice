package com.example.demo;

import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SpringBootApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @Test
    public void getMessagesBySenderValidTest() {
        Assertions.assertTrue(messageService.getMessagesBySender("larisa_pargea@yahoo.com").size() > 0);
    }

    @Test
    public void getMessagesBySenderInvalidTest() {
        Assertions.assertFalse(messageService.getMessagesBySender("larisa_pargea@yahoo.com").size() <= 0);
    }

    @Test
    public void getMessagesByReceiveralidTest() {
        Assertions.assertTrue(messageService.getMessagesByReceiver("larisa_pargea@yahoo.com").size() > 0);
    }

    @Test
    public void getMessagesByReceiverInvalidTest() {
        Assertions.assertFalse(messageService.getMessagesByReceiver("larisa_pargea@yahoo.com").size() <= 0);
    }

    @Test
    public void saveValidTest() {
        Message message = new Message();
        message.setTitle("title");
        message.setText("test");
        Message addedMessage = messageService.save(message, "larisa_pargea@yahoo.com");
        Assertions.assertNotNull(addedMessage);
    }
}
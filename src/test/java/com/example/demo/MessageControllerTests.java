package com.example.demo;

import com.example.demo.controller.MessageController;
import com.example.demo.model.Message;
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
public class MessageControllerTests {

    @Autowired
    private MessageController messageController;

    @Test
    public void getMessagesBySenderValidTest() {
        Assertions.assertTrue(messageController.getMessagesBySender("larisa_pargea@yahoo.com").size() > 0);
    }

    @Test
    public void getMessagesBySenderInvalidTest() {
        Assertions.assertFalse(messageController.getMessagesBySender("larisa_pargea@yahoo.com").size() <= 0);
    }

    @Test
    public void getMessagesByReceiveralidTest() {
        Assertions.assertTrue(messageController.getMessagesByReceiver("larisa_pargea@yahoo.com").size() > 0);
    }

    @Test
    public void getMessagesByReceiverInvalidTest() {
        Assertions.assertFalse(messageController.getMessagesByReceiver("larisa_pargea@yahoo.com").size() <= 0);
    }

    @Test
    public void saveValidTest() {
        Message message = new Message();
        message.setTitle("title");
        message.setText("test");
        Message addedMessage = messageController.save(message, "larisa_pargea@yahoo.com");
        Assertions.assertNotNull(addedMessage);
    }
}